package kg.zavod.Tare.mapper.delivery.price;

import kg.zavod.Tare.domain.delivery.DistrictCapacityPriceEntity;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = DeliveryPriceMapper.class)
public interface DeliveryPriceListMapper {
    List<DeliveryPriceDto> mapToDeliveryPriceDtoList(List<DistrictCapacityPriceEntity> prices);
    List<DeliveryPriceForUpdateDto> mapToDeliveryPriceForUpdateDtoList(List<DistrictCapacityPriceEntity> prices);
}
