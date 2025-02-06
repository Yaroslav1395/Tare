package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.product.product.ProductForUserDto;
import kg.zavod.Tare.service.CertificateService;
import kg.zavod.Tare.service.NoticeService;
import kg.zavod.Tare.service.PartnerService;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final NoticeService noticeService;
    private final PartnerService partnerService;
    private final CertificateService certificateService;
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

    @GetMapping("/")
    public String home(Model model) {
        try {
            logger.info("Отображение главной страницы");
            model.addAttribute("categoriesForCatalog", categoryService.getAllCategories());
            Map<Integer, List<ProductForUserDto>> products= productService.getProductsForHomePage();
            model.addAttribute("productsBottle", products.get(1));
            model.addAttribute("productsJar", products.get(2));
            model.addAttribute("notices", noticeService.getAllNoticesForUser());
            model.addAttribute("partners", partnerService.getAllPartnersForUser());
            model.addAttribute("certificates", certificateService.getAllCertificateForUser());
        }catch (EntitiesNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "home";
    }
}