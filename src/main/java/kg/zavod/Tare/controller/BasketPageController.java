package kg.zavod.Tare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.product.mvc.ProductForOpenBasketDto;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketPageController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(BasketPageController.class);

    @PostMapping("/basket")
    public String basket(Model model, @RequestParam String products) {
        logger.info("Запрос на отображение корзины");
        try {
            model.addAttribute("products", productService.getProductsForBasket(products));
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex){
            logger.error("Ошибка при получении продуктов для корзины: {}", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
        } catch (JsonProcessingException ex) {
            logger.error("Ошибка при преобразовании JSON: " + ex);
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "basket";
    }
}
