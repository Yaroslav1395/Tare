package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.domain.delivery.DistrictCapacityPriceEntity;
import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForSaveDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.delivery.price.DeliveryPriceListMapper;
import kg.zavod.Tare.mapper.delivery.price.DeliveryPriceMapper;
import kg.zavod.Tare.repository.delivery.CapacityRepository;
import kg.zavod.Tare.repository.delivery.DistrictCapacityPriceRepository;
import kg.zavod.Tare.repository.delivery.DistrictRepository;
import kg.zavod.Tare.service.delivery.DistrictCapacityPriceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictCapacityPriceServiceImpl implements DistrictCapacityPriceService {
    private final DistrictCapacityPriceRepository districtCapacityPriceRepository;
    private final DistrictRepository districtRepository;
    private final CapacityRepository capacityRepository;
    private final DeliveryPriceMapper deliveryPriceMapper;
    private final DeliveryPriceListMapper deliveryPriceListMapper;
    private static final Logger logger = LoggerFactory.getLogger(DistrictCapacityPriceServiceImpl.class);

    /**
     * Метод позволяет получить цену доставки по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id цены доставки
     * @return - найденная цена доставки
     */
    @Override
    @Transactional(readOnly = true)
    public DeliveryPriceDto getDeliveryPriceById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска цены доставки по id");
        DistrictCapacityPriceEntity price = districtCapacityPriceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено цены доставки"));
        return deliveryPriceMapper.mapToDeliveryPriceDto(price);
    }

    /**
     * Метод позволяет получить все цены доставки
     * @throws EntitiesNotFoundException - в случае если ни оной цены доставки не найдено
     * @return - список цен доставки
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryPriceDto> getAllDeliveryPrices() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех цен доставки");
        List<DistrictCapacityPriceEntity> prices = districtCapacityPriceRepository.findAll();
        if(prices.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной цены доставки");
        return deliveryPriceListMapper.mapToDeliveryPriceDtoList(prices);
    }

    /**
     * Метод позволяет найти все цены доставки в определенный район
     * @param districtId - id района
     * @return - список цен доставки в определенный район
     * @throws EntitiesNotFoundException - в случае если цен доставки не найдено
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryPriceDto> getAllDeliveryPricesByDistrictId(Integer districtId) throws EntitiesNotFoundException {
        logger.info("Попытка найти все цены доставки в определенный район");
        List<DistrictCapacityPriceEntity> prices = districtCapacityPriceRepository.findAllByDistrictId(districtId);
        if(prices.isEmpty()) throw new EntitiesNotFoundException("Для этого района не найдено цен доставки");
        return deliveryPriceListMapper.mapToDeliveryPriceDtoList(prices);
    }

    /**
     * Метод позволяет найти все цены доставки для определенного допустимого объема
     * @param capacityId - id допустимого объема
     * @return - список цен доставки для определенного допустимого объема
     * @throws EntitiesNotFoundException - в случае если цен доставки не найдено
     */
    @Override
    public List<DeliveryPriceDto> getAllDeliveryPricesByCapacityId(Integer capacityId) throws EntitiesNotFoundException {
        logger.info("Попытка найти все цены доставки для определенного допустимого объема ");
        List<DistrictCapacityPriceEntity> prices = districtCapacityPriceRepository.findAllByCapacityId(capacityId);
        if(prices.isEmpty()) throw new EntitiesNotFoundException("Для этого района не найдено цен доставки");
        return deliveryPriceListMapper.mapToDeliveryPriceDtoList(prices);
    }

    /**
     * Метод позволяет сохранить цену доставки
     * @param deliveryPriceForSaveDto - цена доставки для сохранения
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     * @throws EntityNotFoundException - в случае если район или допустимы объем по id цне найдены
     * @return - сохраненная цена доставки
     */
    @Override
    @Transactional
    public DeliveryPriceDto saveDeliveryPrice(DeliveryPriceForSaveDto deliveryPriceForSaveDto) throws DuplicateEntityException, EntityNotFoundException {
        logger.info("Попытка сохранения цены доставки");
        Optional<DistrictCapacityPriceEntity> duplicatePrice = districtCapacityPriceRepository.findByCapacityIdAndDistrictId(
                deliveryPriceForSaveDto.getCapacityId(), deliveryPriceForSaveDto.getDistrictId());
        if(duplicatePrice.isPresent()) throw new DuplicateEntityException("Такая цена для доставки уже существует. Отредактируйте");
        logger.info("Поиск района по id");
        DistrictEntity district = districtRepository.findById(deliveryPriceForSaveDto.getDistrictId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено района"));
        logger.info("Поиск допустимого объема по id");
        CapacityEntity capacity = capacityRepository.findById(deliveryPriceForSaveDto.getCapacityId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено допустимого объема"));
        DistrictCapacityPriceEntity price = deliveryPriceMapper.mapToPriceEntity(deliveryPriceForSaveDto, district, capacity);
        DistrictCapacityPriceEntity savedPrice = districtCapacityPriceRepository.save(price);
        return deliveryPriceMapper.mapToDeliveryPriceDto(savedPrice);
    }

    /**
     * Метод позволяет редактировать цену доставки
     * @param deliveryPriceForUpdateDto - допустимая цена доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найдена цен доставки для редактирования
     * @return - отредактированная цена доставки
     */
    @Override
    public DeliveryPriceDto updateDeliveryPrice(DeliveryPriceForUpdateDto deliveryPriceForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования цены доставки");
        DistrictCapacityPriceEntity price = districtCapacityPriceRepository.findById(deliveryPriceForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено цены доставки"));
        deliveryPriceMapper.updatePriceEntity(deliveryPriceForUpdateDto, price);
        DistrictCapacityPriceEntity updatedPrice = districtCapacityPriceRepository.save(price);
        return deliveryPriceMapper.mapToDeliveryPriceDto(updatedPrice);
    }

    /**
     * Метод позволяет удалить цену доставки
     * @param id - id цены доставки
     * @return - удалена или нет
     */
    @Override
    public boolean deleteDeliveryPriceById(Integer id) {
        logger.info("Попытка удаления цены доставки");
        districtCapacityPriceRepository.deleteById(id);
        return !districtCapacityPriceRepository.existsById(id);
    }
}
