package kg.zavod.Tare.mapper.certificate;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.dto.certificate.CertificateForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CertificateMapper.class)
public interface CertificateListMapper {
    /**
     * Метод позволяет преобразовать список сущностей сертификатов в DTO для админки
     * @param certificates - список сертификатов
     * @return - список DTO сертификатов
     */
    List<CertificateForAdminDto> mapToCertificateDtoList(List<CertificateEntity> certificates);
}
