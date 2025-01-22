package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.certificate.CertificateForSaveAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.service.CertificateService;
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
public class CertificatePageController {
    private final CategoryService categoryService;
    private final CertificateService certificateService;
    private static final Logger logger = LoggerFactory.getLogger(CertificatePageController.class);

    @GetMapping("/admin/certificates")
    public String certificatesForUserPage(Model model){
        logger.info("Запрос на открытие страницы сертификатов для админки");
        try {
            model.addAttribute("certificates", certificateService.getAllCertificateForAdmin());
            model.addAttribute("certificateSave", new CertificateForSaveAdminDto());
            model.addAttribute("certificateUpdate", new CertificateForUpdateAdminDto());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/certificate";
    }

    @PostMapping("/admin/certificate")
    public String saveCertificate(@ModelAttribute CertificateForSaveAdminDto certificateForSaveAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на сохранение сертификата");
        try {
            certificateService.saveCertificate(certificateForSaveAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Сертификат сохранен");
            return "redirect:/admin/certificates";
        }catch (Exception ex){
            logger.error("Ошибка при сохранении сертификата: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/certificates";
        }
    }

    @PostMapping("/admin/certificate/update")
    public String updateCertificate(@ModelAttribute CertificateForUpdateAdminDto certificateForUpdateAdminDto, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на редактирование сертификата");
        try {
            certificateService.updateCertificate(certificateForUpdateAdminDto);
            redirectAttributes.addFlashAttribute("successMessage", "Сертификат отредактирован");
            return "redirect:/admin/certificates";
        }catch (Exception ex){
            logger.error("Ошибка при редактировании сертификата: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/certificates";
        }
    }

    @PostMapping("/admin/certificate/delete")
    public String deleteCertificate(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        logger.info("Запрос на удаление сертификата");
        try {
            certificateService.deleteCertificateById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Сертификат удалена");
            return "redirect:/admin/certificates";
        } catch (Exception ex){
            logger.error("Ошибка при удалении сертификата: {}", ex.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/certificates";
        }
    }
}
