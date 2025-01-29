package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeForUpdateAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateAdminDto;
import kg.zavod.Tare.service.VacancyService;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class VacancyPageController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(VacancyPageController.class);

    @GetMapping("/vacancy")
    public String vacancyForUserPage(Model model){
        logger.info("Запрос на открытие страницы вакансии для клиента");
        try {
            model.addAttribute("vacancies", vacancyService.getAllVacanciesForUser());
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "vacancy";
    }

    @GetMapping("/admin/vacancies")
    public String vacancyForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы вакансии для админки");
        model.addAttribute("vacancies", vacancyService.getAllVacanciesForAdmin());
        model.addAttribute("vacancySave", new VacancyForSaveAdminDto());
        model.addAttribute("vacancyUpdate", new VacancyForUpdateAdminDto());
        return "admin/vacancy";
    }

    @PostMapping("/admin/vacancy")
    public String saveVacancy(@ModelAttribute VacancyForSaveAdminDto vacancyForSaveAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение вакансии");
        try {
            vacancyService.saveVacancy(vacancyForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Вакансия сохранена");
            return "redirect:/admin/vacancies";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении вакансии: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/vacancies";
        }
    }

    @PostMapping("/admin/vacancy/update")
    public String updateVacancy(@ModelAttribute VacancyForUpdateAdminDto vacancyForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование вакансии");
        try {
            vacancyService.updateVacancy(vacancyForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Вакансия отредактирована");
            return "redirect:/admin/vacancies";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании вакансии: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/vacancies";
        }
    }

    @PostMapping("/admin/vacancy/delete")
    public String deleteVacancy(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление вакансии");
        try {
            vacancyService.deleteVacancyById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Вакансия удалена");
            return "redirect:/admin/vacancies";
        } catch (Exception ex){
            logger.error("Ошибка при удалении вакансии: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/vacancies";
        }
    }
}
