package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateDto;
import kg.zavod.Tare.mapper.partner.PartnerListMapper;
import kg.zavod.Tare.mapper.partner.PartnerMapper;
import kg.zavod.Tare.repository.PartnerRepository;
import kg.zavod.Tare.service.PartnerService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final PartnerListMapper partnerListMapper;
    private static final Logger logger = LoggerFactory.getLogger(PartnerServiceImpl.class);

    /**
     * Метод позволяет получить партнера по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id партнера
     * @return - найденный партнер
     */
    @Override
    public PartnerDto getPartnerById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска партнера по id");
        PartnerEntity partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено партнера по id"));
        return partnerMapper.mapToPartnerDto(partner);
    }

    /**
     * Метод позволяет получить всех партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного партнера не найдено
     * @return - список партнеров
     */
    @Override
    public List<PartnerDto> getAllPartners() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех партнеров");
        List<PartnerEntity> partners = partnerRepository.findAll();
        if(partners.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного партнера");
        return partnerListMapper.mapToPartnerDtoList(partners);
    }

    /**
     * Метод позволяет получить все активных партнеров
     * @return - список партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного активного партнера не найдено
     */
    @Override
    public List<PartnerDto> getAllActivePartners() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех активных партнеров");
        List<PartnerEntity> partners = partnerRepository.findAllByIsActiveTrue();
        if(partners.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного активного партнера");
        return partnerListMapper.mapToPartnerDtoList(partners);
    }

    /**
     * Метод позволяет сохранить партнера
     * @param partnerForSaveDto - партнер для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @return - сохраненный партнер
     */
    @Override
    @Transactional
    public PartnerDto savePartner(PartnerForSaveDto partnerForSaveDto) throws EntityNotFoundException {
        logger.info("Попытка сохранения нового партнера");
        ImageType logoImageType = UtilService.getImageTypeFrom(partnerForSaveDto.getLogoImage());
        ImageType productImageType = UtilService.getImageTypeFrom(partnerForSaveDto.getProductImage());
        PartnerEntity partnerEntity = partnerMapper.mapToPartnerEntity(partnerForSaveDto, logoImageType, productImageType);
        PartnerEntity savedPartners = partnerRepository.save(partnerEntity);
        return partnerMapper.mapToPartnerDto(savedPartners);
    }

    /**
     * Метод позволят редактировать партнера
     * @param partnerForUpdateDto - партнер для редактирования
     * @throws EntityNotFoundException - в случае если не найден партнер для редактирования
     * @return - отредактированный партнер
     */
    @Override
    @Transactional
    public PartnerDto updatePartner(PartnerForUpdateDto partnerForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования партнера");
        PartnerEntity partnerEntity = partnerRepository.findById(partnerForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено новости по id"));
        ImageType logoImageType = UtilService.getImageTypeFrom(partnerForUpdateDto.getLogoImage());
        ImageType productImageType = UtilService.getImageTypeFrom(partnerForUpdateDto.getProductImage());
        partnerMapper.updatePartnerEntity(partnerForUpdateDto, logoImageType, productImageType, partnerEntity);
        PartnerEntity updatedPartner = partnerRepository.save(partnerEntity);
        return partnerMapper.mapToPartnerDto(updatedPartner);
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
