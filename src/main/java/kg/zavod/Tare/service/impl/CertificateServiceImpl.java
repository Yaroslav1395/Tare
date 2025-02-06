package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.certificate.CertificateForAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUserDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final CertificateListMapper certificateListMapper;
    @Value("${file.save.basic.path}")
    private String basicPath;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(VacancyServiceImpl.class);

    /**
     * Метод позволяет получить все сертификаты для клиента
     * @return - список сертификатов
     */
    @Override
    public List<CertificateForUserDto> getAllCertificateForUser() {
        logger.info("Попытка поиска всех сертификатов для клиента");
        List<CertificateEntity> certificates = certificateRepository.findAll();
        List<CertificateForUserDto> certificatesDto = certificateListMapper.mapToCertificateForUserDtoList(certificates);
        certificatesDto.forEach(certificateDto -> {
            certificateDto.setCertificateImage(baseUrlForLoad + certificateDto.getCertificateImage());
            certificateDto.setCertificateImageKg(baseUrlForLoad + certificateDto.getCertificateImageKg());
        });
        return certificatesDto;
    }

    /**
     * Метод позволяет получить все сертификаты для админки
     * @throws EntitiesNotFoundException - в случае если ни оного сертификата не найдено
     * @return - список сертификатов
     */
    @Override
    public List<CertificateForAdminDto> getAllCertificateForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех сертификатов");
        List<CertificateEntity> certificates = certificateRepository.findAll();
        if(certificates.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного сертификата");
        List<CertificateForAdminDto> certificatesDto = certificateListMapper.mapToCertificateDtoList(certificates);
        certificatesDto.forEach(certificateDto -> {
            certificateDto.setCertificateImage(baseUrlForLoad + certificateDto.getCertificateImage());
            certificateDto.setCertificateImageKg(baseUrlForLoad + certificateDto.getCertificateImageKg());
        });
        return certificatesDto;
    }

    /**
     * Метод позволяет сохранить сертификат
     * @param certificateForSaveAdminDto - сертификат для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @throws DuplicateEntityException - в случае если дублируется название сертификата
     */
    @Override
    public void saveCertificate(CertificateForSaveAdminDto certificateForSaveAdminDto) throws EntityNotFoundException, DuplicateEntityException, IOException {
        logger.info("Попытка сохранения нового сертификата");
        boolean isDuplicate = certificateRepository.findByName(certificateForSaveAdminDto.getName()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Сертификат с таким названием уже есть");
        ImageType imageType = UtilService.getImageTypeFrom(certificateForSaveAdminDto.getCertificateImage());
        ImageType imageTypeKg = UtilService.getImageTypeFrom(certificateForSaveAdminDto.getCertificateImageKg());
        String imagePath = UtilService.saveImage(certificateForSaveAdminDto.getCertificateImage(), "certificates", basicPath);
        String imagePathKg = UtilService.saveImage(certificateForSaveAdminDto.getCertificateImageKg(), "certificates", basicPath);
        CertificateEntity certificateEntity = certificateMapper.mapToCertificateEntity(certificateForSaveAdminDto, imageType, imageTypeKg, imagePath, imagePathKg);
        certificateRepository.save(certificateEntity);
    }

    /**
     * Метод позволят редактировать сертификат
     * @param certificateForUpdateAdminDto - сертификат для редактирования
     * @throws EntityNotFoundException - в случае если не найден сертификат для редактирования
     * @throws DuplicateEntityException - в случае если сертификат с таким именем уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    @Override
    public void updateCertificate(CertificateForUpdateAdminDto certificateForUpdateAdminDto) throws EntityNotFoundException, DuplicateEntityException, IOException {
        logger.info("Попытка редактирования сертификата");
        boolean isDuplicate = certificateRepository.findByNameAndIdIsNot(certificateForUpdateAdminDto.getName(), certificateForUpdateAdminDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Сертификат с таким названием уже есть");
        CertificateEntity certificate = certificateRepository.findById(certificateForUpdateAdminDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено сертификата по id"));
        updateCertificateImageRu(certificate, certificateForUpdateAdminDto);
        updateCertificateImageKg(certificate, certificateForUpdateAdminDto);
        certificateMapper.updateCertificateEntity(certificateForUpdateAdminDto, certificate);
        certificateRepository.save(certificate);
    }

    /**
     * Метод позволяет удалять сертификат
     *
     * @param id - id сертификата
     */
    @Override
    public void deleteCertificateById(Integer id) {
        logger.info("Попытка удаления сертификата");
        certificateRepository.deleteById(id);
        certificateRepository.existsById(id);
    }

    /**
     * Метод позволяет обновить картинку сертификата на русском
     * @param certificate - сущность сертификата
     * @param certificateForUpdateAdminDto - объект для редактирования
     * @throws EntityNotFoundException - в случае если тип не поддерживается
     * @throws IOException - в случае если не удалось сохранить сертификат на русском
     */
    private void updateCertificateImageRu(CertificateEntity certificate, CertificateForUpdateAdminDto certificateForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Обновление сертификата на русском");
        if(certificateForUpdateAdminDto.getCertificateImage().getSize() > 1) {
            ImageType imageCertificateRuType = UtilService.getImageTypeFrom(certificateForUpdateAdminDto.getCertificateImage());
            String imageCertificateRuPath = UtilService.saveImage(certificateForUpdateAdminDto.getCertificateImage(), "partners/logo", basicPath);
            String fileName = certificateForUpdateAdminDto.getCertificateImage().getOriginalFilename();
            certificate.setCertificateImage(imageCertificateRuPath);
            certificate.setCertificateImageType(imageCertificateRuType.toString());
            certificate.setCertificateImageName(fileName);
        }
    }

    /**
     * Метод позволяет обновить картинку сертификата на кыргызском
     * @param certificate - сущность сертификата
     * @param certificateForUpdateAdminDto - объект для редактирования
     * @throws EntityNotFoundException - в случае если тип не поддерживается
     * @throws IOException - в случае если не удалось сохранить сертификат на кыргызском
     */
    private void updateCertificateImageKg(CertificateEntity certificate, CertificateForUpdateAdminDto certificateForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Обновление сертификата на кыргызском");
        if(certificateForUpdateAdminDto.getCertificateImageKg().getSize() > 1) {
            ImageType imageCertificateRuType = UtilService.getImageTypeFrom(certificateForUpdateAdminDto.getCertificateImageKg());
            String imageCertificateRuPath = UtilService.saveImage(certificateForUpdateAdminDto.getCertificateImageKg(), "partners/logo", basicPath);
            String fileName = certificateForUpdateAdminDto.getCertificateImageKg().getOriginalFilename();
            certificate.setCertificateImageKg(imageCertificateRuPath);
            certificate.setCertificateImageTypeKg(imageCertificateRuType.toString());
            certificate.setCertificateImageNameKg(fileName);
        }
    }
}
