package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeForSaveAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateAdminDto;
import kg.zavod.Tare.service.NoticeService;
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
public class NoticePageController {
    private final NoticeService noticeService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(NoticePageController.class);

    @GetMapping("/notice")
    public String noticesForUserPage(Model model){
        logger.info("Запрос на открытие страницы новостей для клиента");
        try {
            model.addAttribute("vacancies", noticeService.getAllNoticesForUser());
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "notice";
    }

    @GetMapping("/admin/notices")
    public String noticesForAdminPage(Model model) {
        logger.info("Запрос на открытие страницы новостей для админки");
        model.addAttribute("noticeUpdate", new NoticeForUpdateAdminDto());
        model.addAttribute("noticeSave", new NoticeForSaveAdminDto());
        model.addAttribute("notices", noticeService.getAllNoticesForAdmin());
        return "admin/notice";
    }

    @PostMapping("/admin/notice")
    public String saveNotice(@ModelAttribute NoticeForSaveAdminDto noticeForSaveAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение новости");
        try {
            noticeService.saveNotice(noticeForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Новость сохранена");
            return "redirect:/admin/notices";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении новости: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/notices";
        }
    }

    @PostMapping("/admin/notice/update")
    public String updateNotice(@ModelAttribute NoticeForUpdateAdminDto noticeForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование новости");
        try {
            noticeService.updateNotice(noticeForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Новость отредактирована");
            return "redirect:/admin/notices";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании новости: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/notices";
        }
    }

    @PostMapping("/admin/notice/delete")
    public String deleteNotice(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление новости");
        try {
            noticeService.deleteNoticeById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Новость удалена");
            return "redirect:/admin/notices";
        } catch (Exception ex){
            logger.error("Ошибка при удалении новости: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/notices";
        }
    }
}
