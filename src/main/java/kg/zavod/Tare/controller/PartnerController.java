package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.service.PartnerService;
import kg.zavod.Tare.service.category.CategoryService;
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
public class PartnerController {
    private final PartnerService partnerService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @GetMapping("/partner")
    public String partnersForUserPage(Model model){
        logger.info("Запрос на открытие страницы новостей для клиента");
        try {
            model.addAttribute("partners", partnerService.getAllPartnersForUser());
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "partner";
    }

    @GetMapping("/admin/partners")
    public String partnersForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы партнеров для админки");
        try {
            model.addAttribute("partnerSave", new PartnerForSaveAdminDto());
            model.addAttribute("partnerUpdate", new PartnerForUpdateAdminDto());
            model.addAttribute("partners", partnerService.getAllPartners());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/partner";
    }

    @PostMapping("/admin/partner")
    public String savePartner(@ModelAttribute PartnerForSaveAdminDto partnerForSaveAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение партнера");
        try {
            partnerService.savePartner(partnerForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Партнер сохранен");
            return "redirect:/admin/partners";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении партнера: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/partners";
        }
    }

    @PostMapping("/admin/partner/update")
    public String updatePartner(@ModelAttribute PartnerForUpdateAdminDto partnerForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование партнера");
        try {
            partnerService.updatePartner(partnerForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Партнер отредактирован");
            return "redirect:/admin/partners";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании партнера: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/partners";
        }
    }

    @PostMapping("/admin/partner/delete")
    public String deleteSubcategory(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление партнера");
        try {
            partnerService.deletePartnerById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Партнер удален");
            return "redirect:/admin/partners";
        } catch (Exception ex){
            logger.error("Ошибка при удалении партнера: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/partners";
        }
    }
}
