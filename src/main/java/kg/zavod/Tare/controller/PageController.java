package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final CategoryService categoryService;

    @GetMapping("/") // Главная страница
    public String home(Model model) {
        try {
            model.addAttribute("categories", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "home";
    }

    @GetMapping("/about") // Страница "О нас"
    public String about(Model model) {
        model.addAttribute("title", "About Us");
        return "about"; // Указывает на файл templates/about.html
    }

    @GetMapping("/contact") // Страница "Контакты"
    public String contact(Model model) {
        model.addAttribute("title", "Contact");
        return "contact"; // Указывает на файл templates/contact.html
    }
}