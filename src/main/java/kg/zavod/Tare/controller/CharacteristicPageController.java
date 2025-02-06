package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateAdminDto;
import kg.zavod.Tare.service.product.CharacteristicService;
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
public class CharacteristicPageController {
    private final CharacteristicService characteristicService;
    private static final Logger logger = LoggerFactory.getLogger(CharacteristicPageController.class);

    @GetMapping("/admin/characteristics")
    public String characteristicsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы цветов для админки");
        try {
            model.addAttribute("characteristicSave", new CharacteristicForSaveAdminDto());
            model.addAttribute("characteristicUpdate", new CharacteristicForUpdateAdminDto());
            model.addAttribute("characteristics", characteristicService.getAllCharacteristicsForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/characteristic";
    }

    @PostMapping("/admin/characteristic")
    public String saveCharacteristic(@ModelAttribute CharacteristicForSaveAdminDto characteristic, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение характеристики");
        try {
            characteristicService.saveCharacteristic(characteristic);
            redirectAttributes.addFlashAttribute("successMessage", "Характеристика сохранена");
            return "redirect:/admin/characteristics";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении характеристики: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/characteristics";
        }
    }

    @PostMapping("/admin/characteristic/update")
    public String updateCharacteristic(@ModelAttribute CharacteristicForUpdateAdminDto characteristic, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование характеристики");
        try {
            characteristicService.updateCharacteristic(characteristic);
            redirectAttributes.addFlashAttribute("successMessage", "Характеристика отредактирован");
            return "redirect:/admin/characteristics";
        }
        catch (Exception ex){
            logger.error("Ошибка при редактировании характеристики: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/characteristics";
        }
    }

    @PostMapping("/admin/characteristic/delete")
    public String deleteCharacteristic(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление характеристики");
        try {
            characteristicService.deleteCharacteristicById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Характеристика удалена");
            return "redirect:/admin/characteristics";
        }catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            logger.error("Попытка удаления цвета связанного с продуктами: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Данная характеристика используется продуктами. Удаление невозможно");
            return "redirect:/admin/characteristics";
        } catch (Exception ex) {
            logger.error("Ошибка при удалении характеристики: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/characteristics";
        }
    }
}
