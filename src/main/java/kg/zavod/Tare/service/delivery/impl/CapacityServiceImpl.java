package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.delivery.capacity.CapacityListMapper;
import kg.zavod.Tare.mapper.delivery.capacity.CapacityMapper;
import kg.zavod.Tare.repository.delivery.CapacityRepository;
import kg.zavod.Tare.service.delivery.CapacityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CapacityServiceImpl implements CapacityService {
    private final CapacityRepository capacityRepository;
    private final CapacityMapper capacityMapper;
    private final CapacityListMapper capacityListMapper;
    private static final Logger logger = LoggerFactory.getLogger(CapacityServiceImpl.class);

    /**
     * Метод позволяет получить все допустимые объемы доставки
     * @throws EntitiesNotFoundException - в случае если ни оного допустимого объема доставки не найдено
     * @return - список допустимых объемов доставки
     */
    @Override
    public List<CapacityForAdminDto> getAllCapacities() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех допустимых объемов");
        List<CapacityEntity> capacities = capacityRepository.findAll();
        if(capacities.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного допустимого объема");
        return capacityListMapper.mapToCapacityForAdminDtoList(capacities);
    }

    /**
     * Метод позволяет сохранить допустимый объем доставки
     * @param capacityForSaveAdminDto - допустимый объем доставки для сохранения
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     */
    @Override
    public void saveCapacity(CapacityForSaveAdminDto capacityForSaveAdminDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения допустимого объема");
        Optional<CapacityEntity> duplicateCapacity = capacityRepository.findByCapacityFromAndCapacityTo(
                capacityForSaveAdminDto.getCapacityFrom(), capacityForSaveAdminDto.getCapacityTo());
        if(duplicateCapacity.isPresent()) throw new DuplicateEntityException("Такой допустимый объем уже имеется");
        CapacityEntity capacity = capacityMapper.mapToCapacityEntity(capacityForSaveAdminDto);
        capacityRepository.save(capacity);
    }

    /**
     * Метод позволяет редактировать допустимый объем доставки
     * @param capacityForUpdateAdminDto - допустимый объем доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найден допустимый объем доставки для редактирования
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     */
    @Override
    public void updateCapacity(CapacityForUpdateAdminDto capacityForUpdateAdminDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования допустимого объема");
        Optional<CapacityEntity> duplicateCapacity = capacityRepository.findByCapacityFromAndCapacityTo(
                capacityForUpdateAdminDto.getCapacityFrom(), capacityForUpdateAdminDto.getCapacityTo());
        if(duplicateCapacity.isPresent()) throw new DuplicateEntityException("Такой допустимый объем уже имеется");
        CapacityEntity capacity = capacityRepository.findById(capacityForUpdateAdminDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено допустимого объема по id для редактирования"));
        capacityMapper.updateCapacityEntity(capacityForUpdateAdminDto, capacity);
        capacityRepository.save(capacity);
    }

    /**
     * Метод позволяет удалять допустимый объем доставки
     * @param id - id допустимого объема доставки
     */
    @Override
    public void deleteCapacityById(Integer id) {
        logger.info("Попытка удаления допустимого объема");
        capacityRepository.deleteById(id);
    }
}
