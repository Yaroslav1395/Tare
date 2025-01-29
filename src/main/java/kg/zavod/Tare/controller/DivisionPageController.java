package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateAdminDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.delivery.DivisionService;
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
public class DivisionPageController {
    private final DivisionService divisionService;
    private static final Logger logger = LoggerFactory.getLogger(DivisionPageController.class);

    @GetMapping("/admin/divisions")
    public String capacitiesForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы районов доставки");
        try {
            model.addAttribute("divisionSave", new DivisionForSaveAdminDto());
            model.addAttribute("divisionUpdate", new DivisionForUpdateDto());
            model.addAttribute("divisions", divisionService.getAllDivisionsForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/division";
    }

    @PostMapping("/admin/division")
    public String createCapacity(RedirectAttributes redirectAttributes, @ModelAttribute DivisionForSaveAdminDto divisionForSaveAdminDto) {
        logger.info("Запрос на сохранение района доставки");
        try {
            divisionService.saveDivision(divisionForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Район доставки сохранен");
            return "redirect:/admin/divisions";
        } catch (Exception ex){
            logger.error("Ошибка при сохранении района доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/divisions";
        }
    }

    @PostMapping("/admin/division/update")
    public String updateCategory(@ModelAttribute DivisionForUpdateAdminDto divisionForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование района доставки");
        try {
            divisionService.updateDivision(divisionForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Район доставки отредактирован");
            return "redirect:/admin/divisions";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании района доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/divisions";
        }
    }

    @PostMapping("/admin/division/delete")
    public String deleteCategory(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление допустимого объема");
        try {
            divisionService.deleteDivisionById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Района доставки удален");
            return "redirect:/admin/divisions";
        }catch (ConstraintViolationException ex) {
            logger.error("Попытка удаления района доставки связанного с другими данными: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "У района доставки установлены цены доставки. Удаление невозможно");
            return "redirect:/admin/divisions";
        }catch (Exception ex){
            logger.error("Ошибка при удалении района доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/divisions";
        }
    }
}
