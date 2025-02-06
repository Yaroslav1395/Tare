package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;


public interface DistrictCapacityPriceService {

    /**
     * Метод позволяет сохранить цены доставки для нового района по каждому объему
     * @param district - новый район
     */
    void saveDeliveryPricesForNewDistrict(DistrictEntity district);

    /**
     * Метод позволяет редактировать цену доставки
     *
     * @param deliveryPriceForUpdateDto - допустимая цена доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найдена цен доставки для редактирования
     */
    void updateDeliveryPrice(DeliveryPriceForUpdateDto deliveryPriceForUpdateDto) throws EntityNotFoundException;
}
