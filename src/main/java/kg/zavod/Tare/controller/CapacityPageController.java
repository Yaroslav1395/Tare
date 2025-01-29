package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.delivery.CapacityService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CapacityPageController {
    private final CapacityService capacityService;
    private static final Logger logger = LoggerFactory.getLogger(CapacityPageController.class);

    @GetMapping("/admin/capacities")
    public String capacitiesForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы объемов доставки");
        try {
            model.addAttribute("capacitySave", new CapacityForSaveAdminDto());
            model.addAttribute("capacityUpdate", new CapacityForUpdateAdminDto());
            model.addAttribute("capacities", capacityService.getAllCapacities());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/capacity";
    }

    @PostMapping("/admin/capacity")
    public String createCapacity(RedirectAttributes redirectAttributes, @ModelAttribute CapacityForSaveAdminDto categoryForSaveDto) {
        logger.info("Запрос на сохранение допустимого объема");
        try {
            capacityService.saveCapacity(categoryForSaveDto);
            redirectAttributes.addFlashAttribute("successMessage", "Допустимый объем сохранен");
            return "redirect:/admin/capacities";
        } catch (Exception ex){
            logger.error("Ошибка при сохранении допустимого объема: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/capacities";
        }
    }

    @PostMapping("/admin/capacity/update")
    public String updateCapacity(@ModelAttribute CapacityForUpdateAdminDto capacityForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование допустимого объема");
        try {
            capacityService.updateCapacity(capacityForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Допустимый объем отредактирован");
            return "redirect:/admin/capacities";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании допустимого объема: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/capacities";
        }
    }

    @PostMapping("/admin/capacity/delete")
    public String deleteCapacity(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление допустимого объема");
        try {
            capacityService.deleteCapacityById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Допустимый объем удален");
            return "redirect:/admin/capacities";
        }catch (ConstraintViolationException ex) {
            logger.error("Попытка удаления допустимого объема связанного с другими данными: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "У объема установлены цены доставки. Удаление невозможно");
            return "redirect:/admin/capacities";
        }catch (Exception ex){
            logger.error("Ошибка при удалении допустимого объема: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/capacities";
        }
    }
}
