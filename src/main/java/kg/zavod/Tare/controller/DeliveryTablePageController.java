package kg.zavod.Tare.controller;

import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.deliviry.DeliveryTable;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.delivery.DeliveryTableService;
import kg.zavod.Tare.service.delivery.DistrictCapacityPriceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class DeliveryTablePageController {
    private final DeliveryTableService deliveryTableService;
    private final DistrictCapacityPriceService districtCapacityPriceService;
    private static final Logger logger = LoggerFactory.getLogger(DeliveryTablePageController.class);

    @GetMapping("/delivery")
    public String getDeliveryTableForUser(Model model) {
        logger.info("Получение таблицы доставки для пользователя");
        try {
            model.addAttribute("delivery", deliveryTableService.getDeliveryTable());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "delivery";
    }

    @GetMapping("/admin/delivery/table")
    public String getDeliveryTable(Model model) {
        logger.info("Получение таблицы доставки");
        try {
            model.addAttribute("delivery", deliveryTableService.getDeliveryTable());
        }catch (EntitiesNotFoundException ex){
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "/admin/delivery";
    }

    @PostMapping("/admin/delivery/table")
    public String updateDeliveryTable(@ModelAttribute DeliveryPriceForUpdateDto deliveryPriceForUpdateDto, RedirectAttributes redirectAttributes) {
        logger.info("Редактирование таблицы доставки");
        try {
            districtCapacityPriceService.updateDeliveryPrice(deliveryPriceForUpdateDto);
            redirectAttributes.addFlashAttribute("successMessage", "Цена доставки отредактирована");
            return "redirect:/admin/delivery/table";
        }catch (EntityNotFoundException ex){
            redirectAttributes.addFlashAttribute("errorMessage", "Не найдено цены доставки по id для редактирования");
            return "redirect:/admin/delivery/table";
        }
    }
}
