package kg.zavod.Tare.mapper.delivery.division;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DivisionMapper.class)
public interface DivisionListMapper {
    /**
     * Метод позволяет преобразовать список сущностей территориальных делений в DTO
     * @param divisions - список сущностей
     * @return - список DTO
     */
    List<DivisionForAdminDto> mapToDivisionForAdminDtoList(List<DivisionEntity> divisions);
}
