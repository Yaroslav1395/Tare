package kg.zavod.Tare.controller;

import kg.zavod.Tare.controllerRest.category.CategoryController;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import kg.zavod.Tare.dto.product.color.mvc.ColorForSaveAdminDto;
import kg.zavod.Tare.service.category.SubcategoryService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final SubcategoryService subcategoryService;
    private static final Logger logger = LoggerFactory.getLogger(ProductPageController.class);


    @GetMapping("/admin/products")
    public String colorsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы продуктов для админки");
        try {
            model.addAttribute("products", productService.getProductsForAdmin());
            model.addAttribute("subcategoriesForFilter", subcategoryService.getSubcategoriesForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/product";
    }


    @GetMapping("/product") // Главная страница
    public String home(Model model) {
        /*try {
            logger.info("Отображение главной страницы");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("products", productService.getProductsForHomePage());
            *//*throw new EntitiesNotFoundException("Записи не найдены");*//*
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }*/
        return "product";
    }
}
