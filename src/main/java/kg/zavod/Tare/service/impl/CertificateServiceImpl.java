package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.certificate.CertificateDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.certificate.CertificateListMapper;
import kg.zavod.Tare.mapper.certificate.CertificateMapper;
import kg.zavod.Tare.repository.CertificateRepository;
import kg.zavod.Tare.service.CertificateService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CertificateListMapper certificateListMapper;
    private static final Logger logger = LoggerFactory.getLogger(VacancyServiceImpl.class);

    /**
     * Метод позволяет получить сертификат по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id сертификата
     * @return - найденный сертификат
     */
    @Override
    public CertificateDto getCertificateById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска сертификата по id");
        CertificateEntity certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id сертификата не найдено"));
        return certificateMapper.mapToCertificateDto(certificate);
    }

    /**
     * Метод позволяет получить все сертификаты
     * @throws EntitiesNotFoundException - в случае если ни оного сертификата не найдено
     * @return - список сертификатов
     */
    @Override
    public List<CertificateDto> getAllCertificate() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех сертификатов");
        List<CertificateEntity> certificates = certificateRepository.findAll();
        if(certificates.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного сертификата");
        return certificateListMapper.mapToCertificateDtoList(certificates);
    }

    /**
     * Метод позволяет сохранить сертификат
     * @param certificateForSaveDto - сертификат для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @throws DuplicateEntityException - в случае если дублируется название сертификата
     * @return - сохраненный сертификат
     */
    @Override
    public CertificateDto saveCertificate(CertificateForSaveDto certificateForSaveDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка сохранения нового сертификата");
        boolean isDuplicate = certificateRepository.findByName(certificateForSaveDto.getName()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Сертификат с таким названием уже есть");
        ImageType imageType = UtilService.getImageTypeFrom(certificateForSaveDto.getCertificateImage());
        CertificateEntity certificateEntity = certificateMapper.mapToCertificateEntity(certificateForSaveDto, imageType);
        CertificateEntity savedCertificate = certificateRepository.save(certificateEntity);
        return certificateMapper.mapToCertificateDto(savedCertificate);
    }

    /**
     * Метод позволят редактировать сертификат
     * @param certificateForUpdateDto - сертификат для редактирования
     * @throws EntityNotFoundException - в случае если не найден сертификат для редактирования
     * @throws DuplicateEntityException - в случае если сертификат с таким именем уже существует
     * @return - отредактированный сертификат
     */
    @Override
    public CertificateDto updateCertificate(CertificateForUpdateDto certificateForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования сертификата");
        boolean isDuplicate = certificateRepository.findByNameAndIdIsNot(certificateForUpdateDto.getName(), certificateForUpdateDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Сертификат с таким названием уже есть");
        CertificateEntity certificateEntity = certificateRepository.findById(certificateForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено сертификата по id"));
        ImageType imageType = UtilService.getImageTypeFrom(certificateForUpdateDto.getCertificateImage());
        certificateMapper.updateCertificateEntity(certificateForUpdateDto, imageType, certificateEntity);
        CertificateEntity updatedCertificate = certificateRepository.save(certificateEntity);
        return certificateMapper.mapToCertificateDto(updatedCertificate);
    }

    /**
     * Метод позволяет удалять сертификат
     * @param id - id сертификата
     * @return - удален или нет
     */
    @Override
    public boolean deleteCertificateById(Integer id) {
        logger.info("Попытка удаления сертификата");
        certificateRepository.deleteById(id);
        return !certificateRepository.existsById(id);
    }
}
