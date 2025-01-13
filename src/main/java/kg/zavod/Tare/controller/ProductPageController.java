package kg.zavod.Tare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.zavod.Tare.controllerRest.category.CategoryController;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import kg.zavod.Tare.dto.product.color.mvc.ColorForSaveAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForSaveAdminDto;
import kg.zavod.Tare.dto.product.product.mvc.ProductForSaveAdminDto;
import kg.zavod.Tare.service.category.SubcategoryService;
import kg.zavod.Tare.service.product.CharacteristicService;
import kg.zavod.Tare.service.product.ColorService;
import kg.zavod.Tare.service.product.ProductService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final SubcategoryService subcategoryService;
    private final CharacteristicService characteristicService;
    private final ColorService colorService;
    private static final Logger logger = LoggerFactory.getLogger(ProductPageController.class);


    @GetMapping("/admin/products")
    public String productsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы продуктов для админки");
        List<ImageForSaveAdminDto> imageList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            imageList.add(new ImageForSaveAdminDto());
        }
        try {
            model.addAttribute("products", productService.getProductsForAdmin());
            model.addAttribute("productSave", new ProductForSaveAdminDto(imageList));
            model.addAttribute("subcategoriesForFilter", subcategoryService.getSubcategoriesForAdmin());
            model.addAttribute("characteristics", characteristicService.getAllCharacteristicsForAdmin());
            model.addAttribute("colors", colorService.getAllColorsForAdmin());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/product";
    }

    @PostMapping("/admin/product")
    public String saveProduct(@ModelAttribute ProductForSaveAdminDto productForSaveAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение продукта");
        try {
            productService.saveProductAdminMvc(productForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Продукт сохранен");
            return "redirect:/admin/products";
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            logger.error("Попытка сохранения продукта с существующим артикулом: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Данный артикул уже существует. Сохранение невозможно");
            return "redirect:/admin/products";
        } catch (Exception ex){
            logger.error("Ошибка при сохранении продукта: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/admin/product/delete")
    public String deleteCategory(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление продукта");
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Продукт удален");
            return "redirect:/admin/products";
        }catch (Exception ex){
            logger.error("Ошибка при удалении продукта: {}", ex.getMessage());
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
