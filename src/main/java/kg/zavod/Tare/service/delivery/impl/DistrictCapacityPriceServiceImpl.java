package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.domain.delivery.DistrictCapacityPriceEntity;
import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.delivery.price.DeliveryPriceMapper;
import kg.zavod.Tare.repository.delivery.CapacityRepository;
import kg.zavod.Tare.repository.delivery.DistrictCapacityPriceRepository;
import kg.zavod.Tare.service.delivery.DistrictCapacityPriceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictCapacityPriceServiceImpl implements DistrictCapacityPriceService {
    private final DistrictCapacityPriceRepository districtCapacityPriceRepository;
    private final CapacityRepository capacityRepository;
    private final DeliveryPriceMapper deliveryPriceMapper;
    private static final Logger logger = LoggerFactory.getLogger(DistrictCapacityPriceServiceImpl.class);

    /**
     * Метод позволяет сохранить цены доставки для нового района по каждому объему
     * @param district - новый район
     */
    @Override
    public void saveDeliveryPricesForNewDistrict(DistrictEntity district) {
        logger.info("Попытка сохранения цен доставки для нового района");
        List<CapacityEntity> capacities = capacityRepository.findAll();
        List<DistrictCapacityPriceEntity> prices = new ArrayList<>();
        for (CapacityEntity capacity : capacities) {
            DistrictCapacityPriceEntity price = deliveryPriceMapper.createPriceEntity(district, capacity, null);
            prices.add(price);
        }
        districtCapacityPriceRepository.saveAll(prices);
    }

    /**
     * Метод позволяет редактировать цену доставки
     *
     * @param deliveryPriceForUpdateDto - допустимая цена доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найдена цен доставки для редактирования
     */
    @Override
    public void updateDeliveryPrice(DeliveryPriceForUpdateDto deliveryPriceForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования цены доставки");
        DistrictCapacityPriceEntity price = districtCapacityPriceRepository.findById(deliveryPriceForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено цены доставки"));
        deliveryPriceMapper.updatePriceEntity(deliveryPriceForUpdateDto, price);
        DistrictCapacityPriceEntity updatedPrice = districtCapacityPriceRepository.save(price);
        deliveryPriceMapper.mapToDeliveryPriceDto(updatedPrice);
    }
}
