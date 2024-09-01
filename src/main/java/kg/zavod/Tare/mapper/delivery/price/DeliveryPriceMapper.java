package kg.zavod.Tare.mapper.delivery.price;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.domain.delivery.DistrictCapacityPriceEntity;
import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForSaveDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryPriceMapper {
    @Mapping(target = "capacityId", source = "price.capacity.id")
    @Mapping(target = "districtId", source = "price.district.id")
    DeliveryPriceDto mapToDeliveryPriceDto(DistrictCapacityPriceEntity price);
    @Mapping(target = "district", source = "district")
    @Mapping(target = "capacity", source = "capacity")
    @Mapping(target = "id", ignore = true)
    DistrictCapacityPriceEntity mapToPriceEntity(DeliveryPriceForSaveDto price, DistrictEntity district, CapacityEntity capacity);
    @Mapping(target = "id", ignore = true)
    void updatePriceEntity(DeliveryPriceForUpdateDto deliveryPriceForUpdateDto, @MappingTarget DistrictCapacityPriceEntity price);

    DeliveryPriceForUpdateDto mapToDeliveryPriceForUpdateDto(DistrictCapacityPriceEntity price);
}
