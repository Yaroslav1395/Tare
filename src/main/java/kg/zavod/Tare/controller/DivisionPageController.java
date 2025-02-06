package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateAdminDto;
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
    public String divisionsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы областей доставки");
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
    public String createDivision(RedirectAttributes redirectAttributes, @ModelAttribute DivisionForSaveAdminDto divisionForSaveAdminDto) {
        logger.info("Запрос на сохранение области доставки");
        try {
            divisionService.saveDivision(divisionForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Область доставки сохранена");
            return "redirect:/admin/divisions";
        } catch (Exception ex){
            logger.error("Ошибка при сохранении области доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/divisions";
        }
    }

    @PostMapping("/admin/division/update")
    public String updateDivision(@ModelAttribute DivisionForUpdateAdminDto divisionForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование области доставки");
        try {
            divisionService.updateDivision(divisionForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Область доставки отредактирована");
            return "redirect:/admin/divisions";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании области доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/divisions";
        }
    }

    @PostMapping("/admin/division/delete")
    public String deleteDivision(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление области доставки");
        try {
            divisionService.deleteDivisionById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Область доставки удалена");
            return "redirect:/admin/divisions";
        }catch (ConstraintViolationException ex) {
            logger.error("Попытка удаления района доставки связанного с другими данными: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "У области доставки установлены цены доставки. Удаление невозможно");
            return "redirect:/admin/divisions";
        }catch (Exception ex){
            logger.error("Ошибка при удалении области доставки: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/divisions";
        }
    }
}
