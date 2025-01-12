package kg.zavod.Tare.controller;

import kg.zavod.Tare.controllerRest.category.CategoryController;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import kg.zavod.Tare.dto.product.color.mvc.ColorForSaveAdminDto;
import kg.zavod.Tare.dto.product.product.mvc.ProductForSaveAdminDto;
import kg.zavod.Tare.service.category.SubcategoryService;
import kg.zavod.Tare.service.product.CharacteristicService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final SubcategoryService subcategoryService;
    private final CharacteristicService characteristicService;
    private static final Logger logger = LoggerFactory.getLogger(ProductPageController.class);


    @GetMapping("/admin/products")
    public String productsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы продуктов для админки");
        try {
            model.addAttribute("products", productService.getProductsForAdmin());
            model.addAttribute("productSave", new ProductForSaveAdminDto());
            model.addAttribute("subcategoriesForFilter", subcategoryService.getSubcategoriesForAdmin());
            model.addAttribute("characteristics", characteristicService.getAllCharacteristicsForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/product";
    }

    @PostMapping("/admin/product")
    public String saveProduct(@ModelAttribute ProductForSaveAdminDto productForSaveAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение продукта");
        try {
            redirectAttributes.addFlashAttribute("successMessage", "Продукт сохранен");
            return "redirect:/admin/products";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении продукта: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products";
        }
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
