package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.deliviry.district.DistrictForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.delivery.DistrictService;
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
public class DistrictPageController {
    private final DistrictService districtService;
    private final DivisionService divisionService;
    private static final Logger logger = LoggerFactory.getLogger(DistrictPageController.class);

    @GetMapping("/admin/districts")
    public String districtsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы районов доставки");
        try {
            model.addAttribute("districtSave", new DistrictForSaveAdminDto());
            model.addAttribute("districtUpdate", new DistrictForUpdateAdminDto());
            model.addAttribute("districts", districtService.getAllDistrictsForAdmin());
            model.addAttribute("divisions", divisionService.getAllDivisionsForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/district";
    }

    @PostMapping("/admin/district")
    public String createDistrict(RedirectAttributes redirectAttributes, @ModelAttribute DistrictForSaveAdminDto districtForSaveAdminDto) {
        logger.info("Запрос на сохранение района доставки");
        try {
            districtService.saveDistrict(districtForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Район доставки сохранен");
            return "redirect:/admin/districts";
        } catch (Exception ex){
            logger.error("Ошибка при сохранении района доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/districts";
        }
    }

    @PostMapping("/admin/district/update")
    public String updateDistrict(@ModelAttribute DistrictForUpdateAdminDto districtForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование района доставки");
        try {
            districtService.updateDistrict(districtForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Район доставки отредактирован");
            return "redirect:/admin/districts";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании района доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/districts";
        }
    }

    @PostMapping("/admin/district/delete")
    public String deleteDistrict(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление района доставки");
        try {
            districtService.deleteDistrictById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Района доставки удален");
            return "redirect:/admin/districts";
        }catch (ConstraintViolationException ex) {
            logger.error("Попытка удаления района доставки связанного с другими данными: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "У района доставки установлены цены доставки. Удаление невозможно");
            return "redirect:/admin/districts";
        }catch (Exception ex){
            logger.error("Ошибка при удалении района доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/districts";
        }
    }
}
