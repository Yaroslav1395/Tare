package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CategoryPageController {
    private final CategoryService categoryService;
    @GetMapping("/category") // Главная страница
    public String category(Model model) {
        try {
            model.addAttribute("categories", categoryService.getAllCategories());
            /*throw new EntitiesNotFoundException("Записи не найдены");*/
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "category";
    }
}
