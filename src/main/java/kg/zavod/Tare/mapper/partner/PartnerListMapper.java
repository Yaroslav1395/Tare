package kg.zavod.Tare.mapper.partner;

import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.partner.PartnerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PartnerMapper.class)
public interface PartnerListMapper {
    List<PartnerDto> mapToPartnerDtoList(List<PartnerEntity> partners);
}
