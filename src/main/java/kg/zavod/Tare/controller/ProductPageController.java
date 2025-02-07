package kg.zavod.Tare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForAdminDto;
import kg.zavod.Tare.dto.product.image.ImageForAdminDto;
import kg.zavod.Tare.dto.product.product.ProductForAdminDto;
import kg.zavod.Tare.dto.product.product.ProductForSaveAdminDto;
import kg.zavod.Tare.dto.product.product.ProductForUpdateAdminDto;
import kg.zavod.Tare.service.category.CategoryService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final CharacteristicService characteristicService;
    private final ColorService colorService;
    private static final Logger logger = LoggerFactory.getLogger(ProductPageController.class);



    @GetMapping("/products/subcategory/{subcategoryId}")
    public String products(Model model, @PathVariable Integer subcategoryId) {
        logger.info("Запрос на получение продуктов по подкатегории");
        try {
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
            model.addAttribute("subcategory", subcategoryService.getSubcategoryForUserById(subcategoryId));
            model.addAttribute("products", productService.getProductsForUserBySubcategoryId(subcategoryId));
        } catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        } catch (EntityNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "products";
    }

    @GetMapping("/product/{productId}")
    public String product(Model model, @PathVariable Integer productId) {
        logger.info("Запрос на отображение продукта");
        try {
            model.addAttribute("product", productService.getProductForUserById(productId));
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
        }catch (EntityNotFoundException | EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "product";
    }

    @GetMapping("/product/search")
    public String productSearch(Model model, @RequestParam("search") String search) {
        logger.info("Запрос на поиск продукта");
        try {
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
            model.addAttribute("products", productService.getProductsBySearch(search));
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "products";
    }

    @GetMapping("/admin/products")
    public String productsForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы продуктов для админки");
        try {
            List<ProductForAdminDto> products = productService.getProductsForAdmin();
            model.addAttribute("products", products);
            model.addAttribute("productImagesJson", getJsonProductImagesFrom(products));
            model.addAttribute("productCharacteristicValuesJson", getJsonCharacteristicValuesFrom(products));
            model.addAttribute("productSave", new ProductForSaveAdminDto(6));
            model.addAttribute("productUpdate", new ProductForUpdateAdminDto(6));
            model.addAttribute("subcategoriesForFilter", subcategoryService.getSubcategoriesForAdmin());
            model.addAttribute("characteristics", characteristicService.getAllCharacteristicsForAdmin());
            model.addAttribute("colors", colorService.getAllColorsForAdmin());
        } catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        } catch (JsonProcessingException e) {
            model.addAttribute("errorMessage", "Произошла ошибка при обработке данных json");
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

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute ProductForUpdateAdminDto productForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование продукта");
        try {
            productService.updateProductAdminMvc(productForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Продукт отредактирован");
            return "redirect:/admin/products";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании продукта: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
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

    /**
     * Метод позволяет сгруппировать картинки по продуктам и преобразовывает в json
     * @param products - список продуктов
     * @return - json со списком картинок
     * @throws JsonProcessingException - исключение при преобразовании в json
     */
    private String getJsonProductImagesFrom(List<ProductForAdminDto> products) throws JsonProcessingException {
        if(products == null) return null;
        Map<Integer, List<ImageForAdminDto>> productImagesMap = products.stream()
                .collect(Collectors.toMap(
                        ProductForAdminDto::getId,
                        ProductForAdminDto::getImages
                ));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productImagesMap);
    }

    /**
     * Метод позволяет сгруппировать характеристики по продуктам и преобразовывает в json
     * @param products - список продуктов
     * @return - json со списком характеристик
     * @throws JsonProcessingException - исключение при преобразовании в json
     */
    private String getJsonCharacteristicValuesFrom(List<ProductForAdminDto> products) throws JsonProcessingException {
        if(products == null) return null;
        Map<String, List<CharacteristicValueForAdminDto>> productValuesMap = products.stream()
                .collect(Collectors.toMap(
                        product -> product.getId().toString(),
                        ProductForAdminDto::getCharacteristics
                ));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productValuesMap);
    }
}
