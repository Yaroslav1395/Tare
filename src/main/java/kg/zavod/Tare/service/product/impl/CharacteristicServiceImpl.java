package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.exception.StaticDataException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForAdminDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateAdminDto;
import kg.zavod.Tare.mapper.product.characteristics.CharacteristicListMapper;
import kg.zavod.Tare.mapper.product.characteristics.CharacteristicMapper;
import kg.zavod.Tare.repository.product.CharacteristicRepository;
import kg.zavod.Tare.service.product.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {
    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicMapper characteristicMapper;
    private final CharacteristicListMapper characteristicListMapper;
    private final Set<Integer> staticCharacteristics = new HashSet<>(Arrays.asList(1, 2, 3));
    private static final Logger logger = LoggerFactory.getLogger(CharacteristicServiceImpl.class);

    /**
     * Метод позволяет получить все характеристики. Используется в админке MVC.
     * @return - список характеристик
     * @throws EntitiesNotFoundException - в случае если характеристики не найдены
     */
    @Override
    public List<CharacteristicForAdminDto> getAllCharacteristicsForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка поучения характеристик для админки MVC");
        List<CharacteristicEntity> characteristics = characteristicRepository.findAll();
        if(characteristics.isEmpty()) throw new EntitiesNotFoundException("Характеристики не найдены");
        return characteristicListMapper.mapToCharacteristicDtoListMvc(characteristics);
    }

    /**
     * Метод позволяет сохранить характеристику. Используется в админке MVC.
     * @param characteristicDto - характеристика для сохранения
     * @throws DuplicateEntityException - в случае если характеристика дублируется
     */
    @Override
    @Transactional
    public void saveCharacteristic(CharacteristicForSaveAdminDto characteristicDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения характеристики через админку MVC");
        boolean isDuplicate = characteristicRepository.findByName(characteristicDto.getName()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Характеристика с таким названием уже существует");
        CharacteristicEntity characteristic = characteristicMapper.mapDtoToCharacteristicMvc(characteristicDto);
        characteristicRepository.save(characteristic);
    }

    /**
     * Метод позволяет отредактировать характеристику. Используется в админке MVC.
     * @param characteristicDto - характеристика для редактирования
     * @throws DuplicateEntityException - в случае если характеристика дублируется
     * @throws EntityNotFoundException - в случае если характеристика не найдена
     * @throws StaticDataException - в случае попытки редактирования статичной характеристики
     */
    @Override
    @Transactional
    public void updateCharacteristic(CharacteristicForUpdateAdminDto characteristicDto) throws DuplicateEntityException, StaticDataException, EntityNotFoundException {
        logger.info("Попытка редактирования характеристики через админку MVC");
        boolean isStatic = staticCharacteristics.contains(characteristicDto.getId());
        if(isStatic) throw new StaticDataException("Данную характеристику нельзя редактировать.");
        boolean isDuplicate = characteristicRepository.findByNameAndIdIsNot(characteristicDto.getName(), characteristicDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Характеристика с таким названием уже есть");
        CharacteristicEntity characteristic = characteristicRepository.findById(characteristicDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено характеристики"));
        characteristicMapper.updateCharacteristicEntityMvc(characteristicDto, characteristic);
        characteristicRepository.save(characteristic);
    }

    /**
     * Метод позволяет удалять характеристику
     *
     * @param id - id характеристики
     * @throws StaticDataException - в случае если пытаются удалить статичную характеристику
     */
    @Override
    public void deleteCharacteristicById(Integer id) throws StaticDataException {
        logger.info("Попытка удаления характеристики");
        boolean isStatic = staticCharacteristics.contains(id);
        if(isStatic) throw new StaticDataException("Данную характеристику нельзя удалить.");
        characteristicRepository.deleteById(id);
        characteristicRepository.existsById(id);
    }
}
