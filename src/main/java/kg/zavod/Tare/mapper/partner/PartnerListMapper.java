package kg.zavod.Tare.mapper.partner;

import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.partner.PartnerForAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PartnerMapper.class)
public interface PartnerListMapper {

    /**
     * Метод позволяет преобразовать список сущностей партнеров в список DTO для клиента.
     * @param partners - список сущностей
     * @return - список DTO
     */
    List<PartnerForUserDto> mapToPartnerForUserDtoList(List<PartnerEntity> partners);

    /**
     * Метод позволяет преобразовать список сущностей партнеров в список DTO.
     * Используется в админке
     * @param partners - список сущностей
     * @return - список DTO
     */
    List<PartnerForAdminDto> mapToPartnerDtoList(List<PartnerEntity> partners);
}
