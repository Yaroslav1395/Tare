package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateWithProductDto;
import kg.zavod.Tare.mapper.product.characteristicValue.CharacteristicValueListMapper;
import kg.zavod.Tare.mapper.product.characteristicValue.CharacteristicValueMapper;
import kg.zavod.Tare.repository.product.CharacteristicRepository;
import kg.zavod.Tare.repository.product.CharacteristicValueRepository;
import kg.zavod.Tare.service.product.CharacteristicValueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Метод позволяет сохранить список значений характеристик
     * @param characteristicsValueForSave - список значений характеристик для сохранения
     * @param product - продукт для которого необходимо записать значения характеристик
     * @return - сохраненные значения характеристик
     * @throws EntitiesNotFoundException - в случае если характеристики не будут найдены
     * @throws EntityNotFoundException - в случае если характеристика не будет найдена
     */
    @Override
    @Transactional
    public List<CharacteristicValueDto> saveCharacteristicsValueWithProduct(List<CharacteristicValueForSaveWithProductDto> characteristicsValueForSave, ProductEntity product) throws EntitiesNotFoundException, EntityNotFoundException {
        logger.info("Попытка сохранения значений характеристик продукта");
        Set<Integer> characteristicIds = getCharacteristicIdsFrom(characteristicsValueForSave);
        logger.info("Поиск характеристик по id");
        List<CharacteristicEntity> characteristics = characteristicRepository.findAllById(characteristicIds);
        if(characteristics.isEmpty()) throw new EntitiesNotFoundException("Ни одна характеристика по id не найдена");
        Map<Integer, CharacteristicEntity> characteristicsMap = getCharacteristicMapFrom(characteristics);
        logger.info("Преобразование значений характеристик в сущность");
        List<ProductCharacteristicEntity> characteristicValueEntity = characteristicValueListMapper.mapToCharacteristicValueDtoListForSave(characteristicsValueForSave, product, characteristicsMap, characteristicValueMapper);
        logger.info("Сохранение значений характеристик");
        List<ProductCharacteristicEntity> savedCharacteristicsValue = characteristicValueRepository.saveAll(characteristicValueEntity);
        return characteristicValueListMapper.mapToCharacteristicValueDtoList(savedCharacteristicsValue);
    }

    /**
     * Метод необходим для редактирования значения характеристик продукта совместно с продуктом
     * @param characteristicsValueForUpdate - значения характеристик продукта
     * @param product - продукт, который редактируется
     * @return - отредактированные значения характеристик
     * @throws EntityNotFoundException - в случае если не будут найдены характеристики
     */
    @Override
    @Transactional
    public List<CharacteristicValueDto> updateCharacteristicValueWithProduct(List<CharacteristicValueForUpdateWithProductDto> characteristicsValueForUpdate, ProductEntity product) throws EntityNotFoundException {
        logger.info("Попытка редактирования значений характеристик продукта");
        List<ProductCharacteristicEntity> characteristicsValueFromBase = characteristicValueRepository.findAllByProductId(product.getId());
        List<Integer> characteristicValueForDelete = findCharacteristicForDelete(characteristicsValueFromBase, characteristicsValueForUpdate);
        logger.info("Удаление лишних характеристик");
        characteristicValueRepository.deleteAllById(characteristicValueForDelete);
        Set<Integer> characteristicsId =  getCharacteristicIdsForUpdateFrom(characteristicsValueForUpdate);
        logger.info("Поиск характеристик");
        Map<Integer, CharacteristicEntity> characteristics = getCharacteristicMapFrom(characteristicRepository.findAllById(characteristicsId));
        logger.info("Преобразование значения характеристик в сущности");
        List<ProductCharacteristicEntity> characteristicsValue = characteristicValueListMapper.mapToCharacteristicValueDtoListForUpdate(characteristicsValueForUpdate, product, characteristics, characteristicValueMapper);
        List<ProductCharacteristicEntity> updatedCharacteristicsValue = characteristicValueRepository.saveAll(characteristicsValue);
        return characteristicValueListMapper.mapToCharacteristicValueDtoList(updatedCharacteristicsValue);
    }

    /**
     * Метод позволяет собрать id характеристик из списка значений характеристик
     * @param characteristicsValues - список значений характеристик
     * @return - список id характеристик
     */
    private Set<Integer> getCharacteristicIdsFrom(List<CharacteristicValueForSaveWithProductDto> characteristicsValues){
        logger.info("Получение id характеристик из списка значений характеристик");
        return characteristicsValues.stream()
                .map(CharacteristicValueForSaveWithProductDto::getCharacteristicId)
                .collect(Collectors.toSet());
    }

    /**
     * Метод позволяет собрать id характеристик из списка значений характеристик при редактировании
     * совместно с продуктом
     * @param characteristicsValues - список значений характеристик
     * @return - список id характеристик
     */
    private Set<Integer> getCharacteristicIdsForUpdateFrom(List<CharacteristicValueForUpdateWithProductDto> characteristicsValues){
        logger.info("Получение id характеристик из списка значений характеристик при редактировании совместно с продуктом");
        return characteristicsValues.stream()
                .map(CharacteristicValueForUpdateWithProductDto::getCharacteristicId)
                .collect(Collectors.toSet());
    }

    /**
     * Метод позволяет преобразовать список характеристик в словрь
     * @param characteristics - список характеристик
     * @return - словарь характеристик
     */
    private Map<Integer, CharacteristicEntity> getCharacteristicMapFrom(List<CharacteristicEntity> characteristics){
        return characteristics.stream()
                .collect(Collectors.toMap(CharacteristicEntity::getId, characteristic -> characteristic));
    }

    /**
     * Метод позволяет определить характеристики продукта, которые необходимо удалить
     * @param characteristicFromBase - характеристики продукта из базы
     * @param characteristicsFroUpdate - характеристики продукта при редактировании
     * @return - список id характеристик
     */
    private List<Integer> findCharacteristicForDelete(List<ProductCharacteristicEntity> characteristicFromBase, List<CharacteristicValueForUpdateWithProductDto> characteristicsFroUpdate){
        logger.info("Поиск характеристик которые необходимо удалить при редактировании продукта");
        Set<Integer> characteristicsForUpdateId = characteristicsFroUpdate.stream()
                .map(CharacteristicValueForUpdateWithProductDto::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return characteristicFromBase.stream()
                .map(ProductCharacteristicEntity::getId)
                .filter(id -> !characteristicsForUpdateId.contains(id))
                .collect(Collectors.toList());
    }


}
