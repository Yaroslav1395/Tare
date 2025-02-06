package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionForAdminDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateAdminDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;
    private final DivisionListMapper divisionListMapper;
    private static final Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);

    /**
     * Метод позволяет получить все территориальные деления
     * @throws EntitiesNotFoundException - в случае если ни оного территориального деления не найдено
     * @return - список территориальных делений
     */
    @Override
    public List<DivisionForAdminDto> getAllDivisionsForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка получить все территориальные деления");
        List<DivisionEntity> divisions = divisionRepository.findAll();
        if(divisions.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного территориального деления");
        return divisionListMapper.mapToDivisionForAdminDtoList(divisions);
    }

    /**
     * Метод позволяет сохранить территориальное деление
     * @param divisionForSaveDto - территориальное деление для сохранения
     * @throws DuplicateEntityException - если территориальное деление существует
     */
    @Override
    public void saveDivision(DivisionForSaveAdminDto divisionForSaveDto) throws DuplicateEntityException {
        logger.info("Попытка сохранения территориального деления");
        boolean isDuplicate = divisionRepository.findByName(divisionForSaveDto.getName()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Такое территориальное деление уже существует");
        DivisionEntity division = divisionMapper.mapToDivisionEntity(divisionForSaveDto);
        divisionRepository.save(division);
    }

    /**
     * Метод позволят редактировать территориальное деление
     * @param divisionForUpdateDto - территориальное деление для редактирования
     * @throws EntityNotFoundException - в случае если не найдена территориальное деление для редактирования
     * @throws DuplicateEntityException - если территориальное деление существует
     */
    @Override
    public void updateDivision(DivisionForUpdateAdminDto divisionForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования территориального деления");
        boolean isDuplicate = divisionRepository.findByNameAndIdNot(divisionForUpdateDto.getName(), divisionForUpdateDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Такое территориальное деление уже существует");
        logger.info("Поиск территориального деления по id");
        DivisionEntity division = divisionRepository.findById(divisionForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено территориального деления"));
        divisionMapper.updateDivisionEntity(divisionForUpdateDto, division);
        divisionRepository.save(division);
    }

    /**
     * Метод позволяет удалять территориальное деление
     * @param id - id территориального деления
     */
    @Override
    public void deleteDivisionById(Integer id) {
        logger.info("Попытка удаления территориального деления");
        divisionRepository.deleteById(id);
    }
}
