package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import kg.zavod.Tare.dto.product.color.ColorForSaveAdminDto;
import kg.zavod.Tare.dto.product.color.ColorForUpdateAdminDto;
import kg.zavod.Tare.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ColorPageController {
    private final ColorService colorService;
    private static final Logger logger = LoggerFactory.getLogger(ColorPageController.class);

    @GetMapping("/admin/colors")
    public String colorsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы цветов для админки");
        try {
            model.addAttribute("colorSave", new ColorForSaveAdminDto());
            model.addAttribute("colorUpdate", new ColorForUpdateDto());
            model.addAttribute("colors", colorService.getAllColorsForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/color";
    }

    @PostMapping("/admin/color")
    public String saveColor(@ModelAttribute ColorForSaveAdminDto colorForSaveDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение цвета");
        try {
            colorService.saveColor(colorForSaveDto);
            redirectAttributes.addFlashAttribute("successMessage", "Цвет сохранен");
            return "redirect:/admin/colors";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении цвета: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/colors";
        }
    }

    @PostMapping("/admin/color/update")
    public String updateColor(@ModelAttribute ColorForUpdateAdminDto colorForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование цвета");
        try {
            colorService.updateColor(colorForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Цвет отредактирован");
            return "redirect:/admin/colors";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании цвета: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/colors";
        }
    }

    @PostMapping("/admin/color/delete")
    public String deleteColor(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление цвета");
        try {
            colorService.deleteColorById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Цвет удален");
            return "redirect:/admin/colors";
        }catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            logger.error("Попытка удаления цвета связанного с продуктами: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Данный цвет используется продуктами. Удаление невозможно");
            return "redirect:/admin/colors";
        } catch (Exception ex) {
            logger.error("Ошибка при удалении цвета: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/colors";
        }
    }
}
