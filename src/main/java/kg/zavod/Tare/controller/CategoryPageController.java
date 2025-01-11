package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.category.mvc.CategoryForSaveAdminDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CategoryPageController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryPageController.class);

    @GetMapping("/category") // Главная страница
    public String category(Model model) {
        try {
            model.addAttribute("categories", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "category";
    }

    @GetMapping("/admin/categories")
    public String categoriesForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы категорий для админки");
        try {
            model.addAttribute("categorySave", new CategoryForSaveAdminDto());
            model.addAttribute("categoryUpdate", new CategoryForUpdateAdminDto());
            model.addAttribute("categories", categoryService.getAllCategoriesForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/category";
    }

    @PostMapping("/admin/category")
    public String saveCategory(@ModelAttribute CategoryForSaveAdminDto categoryForSaveDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение категорий");
        try {
            categoryService.saveCategory(categoryForSaveDto);
            redirectAttributes.addFlashAttribute("successMessage", "Категория сохранена");
            return "redirect:/admin/categories";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении категории: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/categories";
        }
    }

    @PostMapping("/admin/category/update")
    public String updateCategory(@ModelAttribute CategoryForUpdateAdminDto categoryForUpdateDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование категорий");
        try {
            categoryService.updateCategory(categoryForUpdateDto);
            redirectAttributes.addFlashAttribute("successMessage", "Категория отредактирована");
            return "redirect:/admin/categories";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании категории: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/categories";
        }
    }

    @PostMapping("/admin/category/delete")
    public String deleteCategory(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление категории");
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Категория удалена");
            return "redirect:/admin/categories";
        }catch (ConstraintViolationException ex) {
            logger.error("Попытка удаления категории связанной с другими данными: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "В данной категории имеются подкатегории. Удаление невозможно");
            return "redirect:/admin/categories";
        }catch (Exception ex){
            logger.error("Ошибка при удалении категории: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/categories";
        }
    }
}
