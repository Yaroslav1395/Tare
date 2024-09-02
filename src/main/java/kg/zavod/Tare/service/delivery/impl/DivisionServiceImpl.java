package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
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
     * Метод позволяет получить территориальное деление по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id территориального деления
     * @return - найденное территориальное деление
     */
    @Override
    @Transactional(readOnly = true)
    public DivisionDto getDivisionById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска территориального деления по id");
        DivisionEntity division = divisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено территориального деления"));
        return divisionMapper.mapToDivisionDto(division);
    }

    /**
     * Метод позволяет получить все территориальные деления
     * @throws EntitiesNotFoundException - в случае если ни оного территориального деления не найдено
     * @return - список территориальных делений
     */
    @Override
    @Transactional(readOnly = true)
    public List<DivisionDto> getAllDivisions() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех территориальных делений");
        List<DivisionEntity> divisions = divisionRepository.findAll();
        if(divisions.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного территориального деления");
        return divisionListMapper.mapToDivisionListDto(divisions);
    }

    /**
     * Метод позволяет сохранить территориальное деление
     * @param divisionForSaveDto - территориальное деление для сохранения
     * @return - сохраненное территориальное деление
     * @throws DuplicateEntityException - если территориальное деление существует
     */
    @Override
    public DivisionDto saveDivision(DivisionForSaveDto divisionForSaveDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения территориального деления");
        boolean isDuplicate = divisionRepository.findByName(divisionForSaveDto.getName()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Такое территориальное деление уже существует");
        DivisionEntity division = divisionMapper.mapToDivisionEntity(divisionForSaveDto);
        DivisionEntity savedDivision = divisionRepository.save(division);
        return divisionMapper.mapToDivisionDto(savedDivision);
    }

    /**
     * Метод позволят редактировать территориальное деление
     * @param divisionForUpdateDto - территориальное деление для редактирования
     * @throws EntityNotFoundException - в случае если не найдена территориальное деление для редактирования
     * @throws DuplicateEntityException - если территориальное деление существует
     * @return - отредактированное территориальное деление
     */
    @Override
    public DivisionDto updateDivision(DivisionForUpdateDto divisionForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования территориального деления");
        boolean isDuplicate = divisionRepository.findByNameAndIdNot(divisionForUpdateDto.getName(), divisionForUpdateDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Такое территориальное деление уже существует");
        logger.info("Поиск территориального деления по id");
        DivisionEntity division = divisionRepository.findById(divisionForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено территориального деления"));
        divisionMapper.updateDivisionEntity(divisionForUpdateDto, division);
        DivisionEntity updatedDivision = divisionRepository.save(division);
        return divisionMapper.mapToDivisionDto(updatedDivision);
    }

    /**
     * Метод позволяет удалять территориальное деление
     * @param id - id территориального деления
     * @return - удалено или нет
     */
    @Override
    public boolean deleteDivisionById(Integer id) {
        logger.info("Попытка удаления территориального деления");
        divisionRepository.deleteById(id);
        return !divisionRepository.existsById(id);
    }
}
