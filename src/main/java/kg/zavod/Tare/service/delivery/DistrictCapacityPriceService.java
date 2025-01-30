package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForSaveDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface DistrictCapacityPriceService {
    /**
     * Метод позволяет получить цену доставки по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id цены доставки
     * @return - найденная цена доставки
     */
    DeliveryPriceDto getDeliveryPriceById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все цены доставки
     * @throws EntitiesNotFoundException - в случае если ни оной цены доставки не найдено
     * @return - список цен доставки
     */
    List<DeliveryPriceDto> getAllDeliveryPrices() throws EntitiesNotFoundException;

    /**
     * Метод позволяет найти все цены доставки в определенный район
     * @param districtId - id района
     * @return - список цен доставки в определенный район
     * @throws EntitiesNotFoundException - в случае если цен доставки не найдено
     */
    List<DeliveryPriceDto> getAllDeliveryPricesByDistrictId(Integer districtId) throws EntitiesNotFoundException;

    /**
     * Метод позволяет найти все цены доставки для определенного допустимого объема
     * @param capacityId - id допустимого объема
     * @return - список цен доставки для определенного допустимого объема
     * @throws EntitiesNotFoundException - в случае если цен доставки не найдено
     */
    List<DeliveryPriceDto> getAllDeliveryPricesByCapacityId(Integer capacityId) throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить цену доставки
     * @param deliveryPriceForSaveDto - цена доставки для сохранения
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     * @throws EntityNotFoundException - в случае если район или допустимы объем по id цне найдены
     * @return - сохраненная цена доставки
     */
    DeliveryPriceDto saveDeliveryPrice(DeliveryPriceForSaveDto deliveryPriceForSaveDto) throws DuplicateEntityException, EntityNotFoundException;

    /**
     * Метод позволяет сохранить цены доставки для нового района по каждому объему
     * @param district - новый район
     */
    void saveDeliveryPricesForNewDistrict(DistrictEntity district);

    /**
     * Метод позволяет редактировать цену доставки
     * @param deliveryPriceForUpdateDto - допустимая цена доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найдена цен доставки для редактирования
     * @return - отредактированная цена доставки
     */
    DeliveryPriceDto updateDeliveryPrice(DeliveryPriceForUpdateDto deliveryPriceForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалить цену доставки
     * @param id - id цены доставки
     * @return - удалена или нет
     */
    boolean deleteDeliveryPriceById(Integer id);
}
