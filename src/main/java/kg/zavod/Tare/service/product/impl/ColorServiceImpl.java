package kg.zavod.Tare.service.product.impl;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.color.ColorForAdminDto;
import kg.zavod.Tare.dto.product.color.ColorForSaveAdminDto;
import kg.zavod.Tare.dto.product.color.ColorForUpdateAdminDto;
import kg.zavod.Tare.mapper.product.color.ColorListMapper;
import kg.zavod.Tare.mapper.product.color.ColorMapper;
import kg.zavod.Tare.repository.product.ColorRepository;
import kg.zavod.Tare.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;
    private final ColorListMapper colorListMapper;
    private static final Logger logger = LoggerFactory.getLogger(ColorServiceImpl.class);


    /**
     * Метод позволяет получить цвета картинок продукта для админки MVC
     * @return - список цветов
     * @throws EntitiesNotFoundException - в случае если цвета не найдены
     */
    @Override
    public List<ColorForAdminDto> getAllColorsForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка получения цветов для админки");
        List<ColorEntity> colors = colorRepository.findAll();
        if(colors.isEmpty()) throw new EntitiesNotFoundException("Цвета не найдены");
        return colorListMapper.mapToColorDtoListForMvc(colors);
    }

    /**
     * Метод позволяет сохранить новый цвет. Используется в админке MVC
     * @param colorForSaveDto - новый цвет
     * @throws DuplicateEntityException - в случае если такой цвет уже существует
     */
    @Override
    @Transactional
    public void saveColor(ColorForSaveAdminDto colorForSaveDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения цвета через админку MVC");
        boolean isDuplicate = colorRepository.findByNameOrHexCode(colorForSaveDto.getName(), colorForSaveDto.getHexCode()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Название цвета или hex код цвета уже существует");
        ColorEntity colorForSave = colorMapper.mapToColorEntityForMvc(colorForSaveDto);
        colorRepository.save(colorForSave);
    }

    /**
     * Метод позволяет отредактировать цвет. Используется в админке MVC
     * @param colorForUpdateAdmin - цвет для редактирования
     * @throws DuplicateEntityException - в случае если такой цвет уже существует
     */
    @Override
    @Transactional
    public void updateColor(ColorForUpdateAdminDto colorForUpdateAdmin) throws DuplicateEntityException, EntityNotFoundException {
        logger.info("Попытка редактирования цвета чрез админку MVC");
        boolean isDuplicate = colorRepository.findByNameOrHexCodeAndIdNot(colorForUpdateAdmin.getName(), colorForUpdateAdmin.getHexCode(),
                colorForUpdateAdmin.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Название цвета или hex код цвета уже существует");
        ColorEntity colorEntity = colorRepository.findById(colorForUpdateAdmin.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено цвета"));
        logger.info("Изменение цвета");
        colorMapper.updateColorEntityForMvc(colorForUpdateAdmin, colorEntity);
        colorRepository.save(colorEntity);
    }

    /**
     * Метод позволяет удалять цвет
     *
     * @param id - id цвет
     */
    @Override
    public void deleteColorById(Integer id) {
        logger.info("Попытка удаления цвета");
        colorRepository.deleteById(id);
        colorRepository.existsById(id);
    }
}
