package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateAdminDto;
import kg.zavod.Tare.mapper.product.characteristicValue.CharacteristicValueListMapper;
import kg.zavod.Tare.mapper.product.characteristicValue.CharacteristicValueMapper;
import kg.zavod.Tare.repository.product.CharacteristicRepository;
import kg.zavod.Tare.repository.product.CharacteristicValueRepository;
import kg.zavod.Tare.service.product.CharacteristicValueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacteristicValueServiceImpl implements CharacteristicValueService {
    private final CharacteristicValueRepository characteristicValueRepository;
    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicValueMapper characteristicValueMapper;
    private final CharacteristicValueListMapper characteristicValueListMapper;
    private static final Logger logger = LoggerFactory.getLogger(ColorServiceImpl.class);


    /**
     * Метод позволяет сохранить список значений характеристик для продукта.
     * Используется в админке MVC
     * @param characteristicsValueForSave - список значений характеристик для сохранения
     * @param product - продукт для которого необходимо записать значения характеристик
     * @throws EntitiesNotFoundException - в случае если характеристики не будут найдены
     */
    @Override
    public void saveCharacteristicsValueMvc(List<CharacteristicValueForSaveAdminDto> characteristicsValueForSave, ProductEntity product) throws EntitiesNotFoundException, EntityNotFoundException {
        logger.info("Попытка сохранения значений характеристик продукта mvc");
        characteristicsValueForSave.removeIf(value -> value.getValue() == null);
        characteristicsValueForSave.removeIf(value -> value.getValue().isEmpty());
        Set<Integer> characteristicIds = getCharacteristicIdsMvcFrom(characteristicsValueForSave);
        logger.info("Поиск характеристик по id mvc");
        List<CharacteristicEntity> characteristics = characteristicRepository.findAllById(characteristicIds);
        if(characteristics.isEmpty()) throw new EntitiesNotFoundException("Ни одна характеристика по id не найдена");
        Map<Integer, CharacteristicEntity> characteristicsMap = getCharacteristicMapFrom(characteristics);
        logger.info("Преобразование значений характеристик в сущность mvc");
        List<ProductCharacteristicEntity> characteristicValueEntity = characteristicValueListMapper.mapToCharacteristicValueDtoListMvcForSave(characteristicsValueForSave, product, characteristicsMap, characteristicValueMapper);
        logger.info("Сохранение значений характеристик mvc");
        characteristicValueRepository.saveAll(characteristicValueEntity);
    }

    /**
     * Метод позволяет отредактировать список значений характеристик для продукта.
     * Используется в админке MVC
     * @param characteristicsValueForUpdate - список значений характеристик для редактирования
     * @param product - продукт для которого необходимо отредактировать значения характеристик
     * @throws EntityNotFoundException - в случае если характеристика не будет найдена
     */
    @Override
    //TODO: отредактировать
    public void updateCharacteristicsValueMvc(List<CharacteristicValueForUpdateAdminDto> characteristicsValueForUpdate, ProductEntity product) throws EntityNotFoundException {
        logger.info("Попытка редактирования значений характеристик продукта mvc");
        List<Integer> characteristicsForDelete = characteristicsValueForUpdate.stream()
                .filter(characteristic -> (characteristic.getValue() == null || characteristic.getValue().isEmpty()) && characteristic.getId() != null)
                .map(CharacteristicValueForUpdateAdminDto::getId)
                .toList();
        characteristicsValueForUpdate.removeIf(characteristic -> characteristic.getValue() == null);
        characteristicsValueForUpdate.removeIf(value -> value.getValue().isEmpty());
        logger.info("Удаление лишних характеристик");
        characteristicValueRepository.deleteAllByIdIn(characteristicsForDelete);
        Set<Integer> characteristicsId =  getCharacteristicIdsForUpdateMvcFrom(characteristicsValueForUpdate);
        logger.info("Поиск характеристик");
        Map<Integer, CharacteristicEntity> characteristics = getCharacteristicMapFrom(characteristicRepository.findAllById(characteristicsId));
        logger.info("Преобразование значения характеристик в сущности");
        List<ProductCharacteristicEntity> characteristicsValue = characteristicValueListMapper.mapToCharacteristicValueDtoListMvcForUpdate(characteristicsValueForUpdate, product, characteristics, characteristicValueMapper);
        characteristicValueRepository.saveAll(characteristicsValue);
    }

    /**
     * Метод позволяет собрать id характеристик из списка значений характеристик
     * @param characteristicsValues - список значений характеристик
     * @return - список id характеристик
     */
    private Set<Integer> getCharacteristicIdsMvcFrom(List<CharacteristicValueForSaveAdminDto> characteristicsValues){
        logger.info("Получение id характеристик из списка значений характеристик mvc");
        return characteristicsValues.stream()
                .map(CharacteristicValueForSaveAdminDto::getCharacteristicId)
                .collect(Collectors.toSet());
    }

    /**
     * Метод позволяет собрать id характеристик из списка значений характеристик при редактировании
     * совместно с продуктом
     * @param characteristicsValues - список значений характеристик
     * @return - список id характеристик
     */
    private Set<Integer> getCharacteristicIdsForUpdateMvcFrom(List<CharacteristicValueForUpdateAdminDto> characteristicsValues){
        logger.info("Получение id характеристик из списка значений характеристик при редактировании совместно с продуктом mvc");
        return characteristicsValues.stream()
                .map(CharacteristicValueForUpdateAdminDto::getCharacteristicId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Метод позволяет преобразовать список характеристик в словарь
     * @param characteristics - список характеристик
     * @return - словарь характеристик
     */
    private Map<Integer, CharacteristicEntity> getCharacteristicMapFrom(List<CharacteristicEntity> characteristics){
        return characteristics.stream()
                .collect(Collectors.toMap(CharacteristicEntity::getId, characteristic -> characteristic));
    }
}
