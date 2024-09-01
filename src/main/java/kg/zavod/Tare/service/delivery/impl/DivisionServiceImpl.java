package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.delivery.division.DivisionListMapper;
import kg.zavod.Tare.mapper.delivery.division.DivisionMapper;
import kg.zavod.Tare.repository.delivery.DivisionRepository;
import kg.zavod.Tare.service.delivery.DivisionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;
    private final DivisionListMapper divisionListMapper;
    private static final Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);

    /**
     * Метод позволяет получить териториальное деление по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id териториального деления
     * @return - найденное териториальное деление
     */
    @Override
    @Transactional(readOnly = true)
    public DivisionDto getDivisionById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска териториального деления по id");
        DivisionEntity division = divisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено териториального деления"));
        return divisionMapper.mapToDivisionDto(division);
    }

    /**
     * Метод позволяет получить все териториальные деления
     * @throws EntitiesNotFoundException - в случае если ни оного териториального деления не найдено
     * @return - список териториальных делений
     */
    @Override
    @Transactional(readOnly = true)
    public List<DivisionDto> getAllDivisions() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех териториальных делений");
        List<DivisionEntity> divisions = divisionRepository.findAll();
        if(divisions.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного териториального деления");
        return divisionListMapper.mapToDivisionListDto(divisions);
    }

    /**
     * Метод позволяет сохранить териториальное деление
     * @param divisionForSaveDto - териториальное деление для сохранения
     * @return - сохраненное териториальное деление
     */
    @Override
    public DivisionDto saveDivision(DivisionForSaveDto divisionForSaveDto) {
        logger.info("Попытка сохранения териториального деления");
        DivisionEntity division = divisionMapper.mapToDivisionEntity(divisionForSaveDto);
        DivisionEntity savedDivision = divisionRepository.save(division);
        return divisionMapper.mapToDivisionDto(savedDivision);
    }

    /**
     * Метод позволят редактировать териториальное деление
     * @param divisionForUpdateDto - териториальное деление для редактирования
     * @throws EntityNotFoundException - в случае если не найдена териториальное деление для редактирования
     * @return - отредактированное териториальное деление
     */
    @Override
    public DivisionDto updateDivision(DivisionForUpdateDto divisionForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования териториального деления");
        logger.info("Поиск териториального деления по id");
        DivisionEntity division = divisionRepository.findById(divisionForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено териториального деления"));
        divisionMapper.updateDivisionEntity(divisionForUpdateDto, division);
        DivisionEntity updatedDivision = divisionRepository.save(division);
        return divisionMapper.mapToDivisionDto(updatedDivision);
    }

    /**
     * Метод позволяет удалять териториальное деление
     * @param id - id териториального деления
     * @return - удалено или нет
     */
    @Override
    public boolean deleteDivisionById(Integer id) {
        logger.info("Попытка удаления териториального деления");
        divisionRepository.deleteById(id);
        return !divisionRepository.existsById(id);
    }
}
