package kg.zavod.Tare.controller;

import kg.zavod.Tare.controllerRest.category.CategoryController;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

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
