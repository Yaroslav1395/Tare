package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.exception.StaticDataException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForAdminDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForUpdateAdminDto;
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
     * Метод позволяет получить характеристику по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id характеристики
     * @return - характеристика
     */
    @Override
    public CharacteristicDto getCharacteristicById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска цвета по id");
        CharacteristicEntity characteristic = characteristicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено характеристики"));
        return characteristicMapper.mapToCharacteristicDto(characteristic);
    }

    /**
     * Метод позволяет получить все характеристики
     * @throws EntitiesNotFoundException - в случае если ни оной характеристики не найдено
     * @return - список характеристик
     */
    @Override
    public List<CharacteristicDto> getAllCharacteristics() throws EntitiesNotFoundException {
        logger.info("Попытка получить все характеристики");
        List<CharacteristicEntity> characteristics = characteristicRepository.findAll();
        if(characteristics.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной характеристики");
        return characteristicListMapper.mapToCharacteristicDtoList(characteristics);
    }

    /**
     * Метод позволяет сохранить характеристику
     * @param characteristicForSaveDto - характеристика для сохранения
     * @return - сохраненная характеристика
     * @throws DuplicateEntityException - в случае если характеристика с таким названием уже есть
     */
    @Override
    public CharacteristicDto saveCharacteristic(CharacteristicForSaveDto characteristicForSaveDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения характеристики");
        boolean isDuplicate = characteristicRepository.findByName(characteristicForSaveDto.getName()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Характеристика с таким названием уже есть");
        CharacteristicEntity characteristic = characteristicMapper.mapToCharacteristicEntity(characteristicForSaveDto);
        CharacteristicEntity savedCharacteristic = characteristicRepository.save(characteristic);
        return characteristicMapper.mapToCharacteristicDto(savedCharacteristic);
    }

    /**
     * Метод позволят редактировать характеристику
     * @param characteristicForUpdateDto - характеристика для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдена характеристика
     * @throws DuplicateEntityException - в случае если характеристика с таким названием уже есть
     * @throws StaticDataException - в случае если пытаются отредактировать статичную характеристику
     * @return - отредактированная характеристика
     */
    @Override
    public CharacteristicDto updateCharacteristic(CharacteristicForUpdateDto characteristicForUpdateDto) throws EntityNotFoundException, DuplicateEntityException, StaticDataException {
        logger.info("Попытка редактирования характеристики");
        boolean isStatic = staticCharacteristics.contains(characteristicForUpdateDto.getId());
        if(isStatic) throw new StaticDataException("Данную характеристику нельзя редактировать.");
        boolean isDuplicate = characteristicRepository.findByNameAndIdIsNot(characteristicForUpdateDto.getName(), characteristicForUpdateDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Характеристика с таким id уже есть");
        CharacteristicEntity characteristic = characteristicRepository.findById(characteristicForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено характеристики"));
        characteristicMapper.updateCharacteristicEntity(characteristicForUpdateDto, characteristic);
        CharacteristicEntity updatedCharacteristic = characteristicRepository.save(characteristic);
        return  characteristicMapper.mapToCharacteristicDto(updatedCharacteristic);
    }

    /**
     * Метод позволяет удалять характеристику
     * @param id - id характеристики
     * @throws StaticDataException - в случае если пытаются удалить статичную характеристику
     * @return - удалена или нет
     */
    @Override
    public Boolean deleteCharacteristicById(Integer id) throws StaticDataException {
        logger.info("Попытка удаления характеристики");
        boolean isStatic = staticCharacteristics.contains(id);
        if(isStatic) throw new StaticDataException("Данную характеристику нельзя удалить.");
        characteristicRepository.deleteById(id);
        return !characteristicRepository.existsById(id);
    }
}
