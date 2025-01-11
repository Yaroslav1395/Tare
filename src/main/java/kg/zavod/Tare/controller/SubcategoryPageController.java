package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.category.mvc.CategoryForSaveAdminDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.category.SubcategoryService;
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
public class SubcategoryPageController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private static final Logger logger = LoggerFactory.getLogger(SubcategoryPageController.class);

    @GetMapping("/admin/subcategories")
    public String subcategoriesForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы подкатегорий для админки");
        try {
            model.addAttribute("subcategorySave", new SubcategoryForSaveAdminDto());
            model.addAttribute("subcategoryUpdate", new SubcategoryForUpdateAdminDto());
            model.addAttribute("categories", categoryService.getAllCategoriesForAdmin());
            model.addAttribute("subcategories", subcategoryService.getSubcategoriesForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/subcategory";
    }

    @PostMapping("/admin/subcategory")
    public String saveSubcategory(@ModelAttribute SubcategoryForSaveAdminDto subcategoryForSaveDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение подкатегорий");
        try {
            subcategoryService.saveSubcategory(subcategoryForSaveDto);
            redirectAttributes.addFlashAttribute("successMessage", "Подкатегория сохранена");
            return "redirect:/admin/subcategories";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении подкатегории: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/subcategories";
        }
    }

    @PostMapping("/admin/subcategory/update")
    public String updateCategory(@ModelAttribute SubcategoryForUpdateAdminDto subcategoryForUpdateDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование категорий");
        try {
            subcategoryService.updateSubcategory(subcategoryForUpdateDto);
            redirectAttributes.addFlashAttribute("successMessage", "Подкатегория отредактирована");
            return "redirect:/admin/subcategories";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании подкатегории: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/subcategories";
        }
    }

    @PostMapping("/admin/subcategory/delete")
    public String deleteSubcategory(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление подкатегории");
        try {
            subcategoryService.deleteSubcategoryById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Подкатегория удалена");
            return "redirect:/admin/subcategories";
        }catch (ConstraintViolationException ex) {
            logger.error("Попытка удаления категории связанной с другими данными: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "В данной подкатегории имеются продукты. Удаление невозможно");
            return "redirect:/admin/subcategories";
        }catch (Exception ex){
            logger.error("Ошибка при удалении категории: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/subcategories";
        }
    }
}
