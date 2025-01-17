package kg.zavod.Tare.controller;

import kg.zavod.Tare.controllerRest.category.CategoryController;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/") // Главная страница
    public String home(Model model) {
        try {
            logger.info("Отображение главной страницы");
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
            model.addAttribute("products", productService.getProductsForHomePage());
            /*throw new EntitiesNotFoundException("Записи не найдены");*/
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "home";
    }
}