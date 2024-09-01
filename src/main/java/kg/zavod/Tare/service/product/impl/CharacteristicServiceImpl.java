package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateDto;
import kg.zavod.Tare.mapper.product.characteristics.CharacteristicListMapper;
import kg.zavod.Tare.mapper.product.characteristics.CharacteristicMapper;
import kg.zavod.Tare.repository.product.CharacteristicRepository;
import kg.zavod.Tare.service.product.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {
    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicMapper characteristicMapper;
    private final CharacteristicListMapper characteristicListMapper;
    private static final Logger logger = LoggerFactory.getLogger(CharacteristicServiceImpl.class);

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
     */
    @Override
    public CharacteristicDto saveCharacteristic(CharacteristicForSaveDto characteristicForSaveDto) {
        logger.info("Попытка сохранения характеристики");
        CharacteristicEntity characteristic = characteristicMapper.mapToCharacteristicEntity(characteristicForSaveDto);
        CharacteristicEntity savedCharacteristic = characteristicRepository.save(characteristic);
        return characteristicMapper.mapToCharacteristicDto(savedCharacteristic);
    }

    /**
     * Метод позволят редактировать характеристику
     * @param characteristicForUpdateDto - характеристика для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдена характеристика
     * @return - отредактированная характеристика
     */
    @Override
    public CharacteristicDto updateCharacteristic(CharacteristicForUpdateDto characteristicForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования характеристики");
        CharacteristicEntity characteristic = characteristicRepository.findById(characteristicForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено характеристики"));
        characteristicMapper.updateCharacteristicEntity(characteristicForUpdateDto, characteristic);
        CharacteristicEntity updatedCharacteristic = characteristicRepository.save(characteristic);
        return  characteristicMapper.mapToCharacteristicDto(updatedCharacteristic);
    }

    /**
     * Метод позволяет удалять характеристику
     * @param id - id характеристики
     * @return - удалена или нет
     */
    @Override
    public Boolean deleteCharacteristicById(Integer id) {
        logger.info("Попытка удаления характеристики");
        characteristicRepository.deleteById(id);
        return !characteristicRepository.existsById(id);
    }
}
