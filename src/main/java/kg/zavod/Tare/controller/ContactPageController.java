package kg.zavod.Tare.controller;

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
public class ContactPageController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(ContactPageController.class);

    @GetMapping("/contact")
    public String noticesForUserPage(Model model){
        logger.info("Запрос на открытие страницы контакты для клиента");
        try {
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "contact";
    }
}
