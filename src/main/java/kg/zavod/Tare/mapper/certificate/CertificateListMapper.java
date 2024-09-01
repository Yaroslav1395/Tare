package kg.zavod.Tare.mapper.certificate;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.dto.certificate.CertificateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CertificateMapper.class)
public interface CertificateListMapper {
    List<CertificateDto> mapToCertificateDtoList(List<CertificateEntity> certificates);
}
