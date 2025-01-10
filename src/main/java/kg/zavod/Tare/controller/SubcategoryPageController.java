package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.category.mvc.CategoryForSaveAdminDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SubcategoryPageController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(SubcategoryPageController.class);

    @GetMapping("/admin/subcategories")
    public String categoriesForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы подкатегорий для админки");
        try {
            model.addAttribute("categorySave", new SubcategoryForSaveAdminDto());
            model.addAttribute("categoryUpdate", new SubcategoryForUpdateAdminDto());
            model.addAttribute("categories", categoryService.getAllCategoriesForAdminPage());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/subcategory";
    }
}
