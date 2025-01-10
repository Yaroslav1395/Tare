package kg.zavod.Tare.controller;

import kg.zavod.Tare.controllerRest.category.CategoryController;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminPageController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);
    @GetMapping("/admin") // Главная страница
    public String category(Model model) {
        logger.info("Запрос на открытие админки");
        /*try {
            model.addAttribute("categories", categoryService.getAllCategories());
            *//*throw new EntitiesNotFoundException("Записи не найдены");*//*
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }*/
        return "admin/category";
    }
}
