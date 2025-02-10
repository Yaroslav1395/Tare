package kg.zavod.Tare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;


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
            logger.error("Ошибка при преобразовании JSON: {}", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "basket";
    }

    @PostMapping("/basket/confirm")
    public ResponseEntity<Void> confirmBasket(Model model, @RequestParam String products) {
        logger.info("Запрос на оформление заявки");
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(productService.buildUrlForWhatsAppBid(products)))
                    .build();
        }catch (IOException ex) {
            logger.error("Ошибка при формировании url для WhatsApp: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/")
                    .build();
        }
    }
}
