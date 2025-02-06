package kg.zavod.Tare.mapper.delivery.district;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DistrictMapper.class)
public interface DistrictListMapper {
    /**
     * Метод позволяет преобразовать список сущностей районов в список районов DTO
     * @param districts - список сущностей
     * @return - список DTO
     */
    List<DistrictForAdminDto> mapToDistrictForAdminDtoList(List<DistrictEntity> districts);
}
