package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateDto;
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
     * Метод позволяет получить допустимый объем доставки по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id допустимого объема доставки
     * @return - найденный допустимый объем доставки
     */
    @Override
    public CapacityDto getCapacityById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска допустимого объема по id");
        CapacityEntity capacity = capacityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено допустимого объема"));
        return capacityMapper.mapToCapacityDto(capacity);
    }

    /**
     * Метод позволяет получить все допустимые объемы доставки
     * @throws EntitiesNotFoundException - в случае если ни оного допустимого объема доставки не найдено
     * @return - список допустимых объемов доставки
     */
    @Override
    public List<CapacityDto> getAllCapacities() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех допустимых объемов");
        List<CapacityEntity> capacities = capacityRepository.findAll();
        if(capacities.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного допустимого объема");
        return capacityListMapper.mapToCapacityListMapper(capacities);
    }

    /**
     * Метод позволяет сохранить допустимый объем доставки
     * @param capacityForSaveDto - допустимый объем доставки для сохранения
     * @return - сохраненный допустимый объем доставки
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     */
    @Override
    public CapacityDto saveCapacity(CapacityForSaveDto capacityForSaveDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения допустимого объема");
        Optional<CapacityEntity> duplicateCapacity = capacityRepository.findByCapacityFromAndCapacityTo(
                capacityForSaveDto.getCapacityFrom(), capacityForSaveDto.getCapacityTo());
        if(duplicateCapacity.isPresent()) throw new DuplicateEntityException("Такой допустимый объем уже имеется");
        CapacityEntity capacity = capacityMapper.mapToCapacityEntity(capacityForSaveDto);
        CapacityEntity savedCapacity = capacityRepository.save(capacity);
        return capacityMapper.mapToCapacityDto(savedCapacity);
    }

    /**
     * Метод позволяет редактировать допустимый объем доставки
     * @param capacityForUpdateDto - допустимый объем доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найден допустимый объем доставки для редактирования
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     * @return - отредактированное допустимый объем доставки
     */
    @Override
    public CapacityDto updateCapacity(CapacityForUpdateDto capacityForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования допустимого объема");
        Optional<CapacityEntity> duplicateCapacity = capacityRepository.findByCapacityFromAndCapacityTo(
                capacityForUpdateDto.getCapacityFrom(), capacityForUpdateDto.getCapacityTo());
        if(duplicateCapacity.isPresent()) throw new DuplicateEntityException("Такой допустимый объем уже имеется");
        CapacityEntity capacity = capacityRepository.findById(capacityForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено допустимого объема по id для редактирования"));
        capacityMapper.updateCapacityEntity(capacityForUpdateDto, capacity);
        CapacityEntity updatedCapacity = capacityRepository.save(capacity);
        return capacityMapper.mapToCapacityDto(updatedCapacity);
    }

    /**
     * Метод позволяет удалять допустимый объем доставки
     * @param id - id допустимого объема доставки
     * @return - удален или нет
     */
    @Override
    public boolean deleteCapacityById(Integer id) {
        logger.info("Попытка удаления допустимого объема");
        capacityRepository.deleteById(id);
        return !capacityRepository.existsById(id);
    }
}
