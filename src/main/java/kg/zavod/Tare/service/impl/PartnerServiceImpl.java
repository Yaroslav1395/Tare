package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerForAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.mapper.partner.PartnerListMapper;
import kg.zavod.Tare.mapper.partner.PartnerMapper;
import kg.zavod.Tare.repository.PartnerRepository;
import kg.zavod.Tare.service.PartnerService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final PartnerListMapper partnerListMapper;
    @Value("${file.save.basic.path}")
    private String basicPath;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(PartnerServiceImpl.class);

    /**
     * Метод позволяет получить всех партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного партнера не найдено
     * @return - список партнеров
     */
    @Override
    public List<PartnerForAdminDto> getAllPartners() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех партнеров");
        List<PartnerEntity> partners = partnerRepository.findAll();
        if(partners.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного партнера");
        List<PartnerForAdminDto> partnersDto = partnerListMapper.mapToPartnerDtoList(partners);
        partnersDto.forEach(partner -> {
            partner.setLogoImage(baseUrlForLoad + partner.getLogoImage());
            partner.setProductImage(baseUrlForLoad + partner.getProductImage());
        });
        return partnersDto;
    }

    /**
     * Метод позволяет сохранить партнера
     * @param partnerForSaveAdminDto - партнер для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     */
    @Override
    @Transactional
    public void savePartner(PartnerForSaveAdminDto partnerForSaveAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Попытка сохранения нового партнера");
        ImageType logoImageType = UtilService.getImageTypeFrom(partnerForSaveAdminDto.getLogoImage());
        ImageType productImageType = partnerForSaveAdminDto.getProductImage().getSize() > 1 ?
                UtilService.getImageTypeFrom(partnerForSaveAdminDto.getProductImage()) : null;
        String logoPath = UtilService.saveImage(partnerForSaveAdminDto.getLogoImage(), "partners/logo", basicPath);
        String productPath = partnerForSaveAdminDto.getProductImage().getSize() > 1 ?
                UtilService.saveImage(partnerForSaveAdminDto.getProductImage(), "partners/product", basicPath) : null;
        PartnerEntity partnerEntity = partnerMapper.mapToPartnerEntity(partnerForSaveAdminDto, logoImageType, logoPath, productImageType, productPath);
        partnerRepository.save(partnerEntity);
    }

    /**
     * Метод позволят редактировать партнера
     * @param partnerForUpdateAdminDto - партнер для редактирования
     * @throws EntityNotFoundException - в случае если не найден партнер для редактирования
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    @Override
    @Transactional
    public void updatePartner(PartnerForUpdateAdminDto partnerForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Попытка редактирования партнера");
        PartnerEntity partnerEntity = partnerRepository.findById(partnerForUpdateAdminDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено партнера по id"));
        partnerMapper.updatePartnerEntity(partnerForUpdateAdminDto, partnerEntity);
        updatePartnerLogo(partnerEntity, partnerForUpdateAdminDto);
        updatePartnerProduct(partnerEntity, partnerForUpdateAdminDto);
        partnerRepository.save(partnerEntity);
    }

    /**
     * Метод позволяет удалять партнера
     * @param id - id партнера
     * @return - удален или нет
     */
    @Override
    @Transactional
    public boolean deletePartnerById(Integer id) {
        logger.info("Попытка удаления партнера");
        partnerRepository.deleteById(id);
        return !partnerRepository.existsById(id);
    }

    /**
     * Метод позволяет обновить логотип партнера
     * @param partner - сущность партнера
     * @param partnerForUpdateAdminDto - объект для редактирования
     * @throws EntityNotFoundException - в случае если тип не поддерживается
     * @throws IOException - в случае если не удалось сохранить логотип
     */
    private void updatePartnerLogo(PartnerEntity partner, PartnerForUpdateAdminDto partnerForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Обновление логотипа партнера");
        if(partnerForUpdateAdminDto.getLogoImage().getSize() > 1) {
            ImageType logoImageType = UtilService.getImageTypeFrom(partnerForUpdateAdminDto.getLogoImage());
            String logoPath = UtilService.saveImage(partnerForUpdateAdminDto.getLogoImage(), "partners/logo", basicPath);
            String fileName = partnerForUpdateAdminDto.getLogoImage().getOriginalFilename();
            partner.setLogoImage(logoPath);
            partner.setLogoImageType(fileName);
            partner.setLogoImageType(logoImageType.toString());
        }
    }

    /**
     * Метод позволяет обновить продукцию партнера
     * @param partner - сущность партнера
     * @param partnerForUpdateAdminDto - объект для редактирования
     * @throws EntityNotFoundException - в случае если тип не поддерживается
     * @throws IOException - в случае если не удалось сохранить продукцию
     */
    private void updatePartnerProduct(PartnerEntity partner, PartnerForUpdateAdminDto partnerForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Обновление продукции партнера");
        if(partnerForUpdateAdminDto.getProductImage().getSize() > 1) {
            ImageType productImageType = UtilService.getImageTypeFrom(partnerForUpdateAdminDto.getProductImage());
            String productPath = UtilService.saveImage(partnerForUpdateAdminDto.getProductImage(), "partners/product", basicPath);
            String fileName = partnerForUpdateAdminDto.getProductImage().getOriginalFilename();
            partner.setProductImage(productPath);
            partner.setProductImageType(productImageType.toString());
            partner.setProductImageName(fileName);
        }
    }












    /**
     * Метод позволяет получить партнера по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id партнера
     * @return - найденный партнер
     */
    @Override
    public PartnerForAdminDto getPartnerById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска партнера по id");
        PartnerEntity partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено партнера по id"));
        return partnerMapper.mapToPartnerDto(partner);
    }

    /**
     * Метод позволяет получить все активных партнеров
     * @return - список партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного активного партнера не найдено
     */
    @Override
    public List<PartnerForAdminDto> getAllActivePartners() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех активных партнеров");
        List<PartnerEntity> partners = partnerRepository.findAllByIsActiveTrue();
        if(partners.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного активного партнера");
        return partnerListMapper.mapToPartnerDtoList(partners);
    }

    /**
     * Метод позволяет изменять активность партнера
     * @param id - id партнера активность которого нужно изменить
     * @param isActive - флаг активности
     * @return - текущее состояние партнера
     */
    @Override
    @Transactional
    public boolean changePartnerActivityById(Integer id, Boolean isActive) {
        logger.info("Попытка изменения активности партнера");
        int active = partnerRepository.updateIsActiveById(id, isActive);
        return active == 1;
    }
}
