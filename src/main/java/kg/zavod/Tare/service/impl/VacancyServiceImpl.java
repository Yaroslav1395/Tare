package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.vacancy.VacancyDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateDto;
import kg.zavod.Tare.mapper.vacancy.VacancyListMapper;
import kg.zavod.Tare.mapper.vacancy.VacancyMapper;
import kg.zavod.Tare.repository.VacancyRepository;
import kg.zavod.Tare.service.VacancyService;
import kg.zavod.Tare.service.category.impl.SubcategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final VacancyListMapper vacancyListMapper;
    private static final Logger logger = LoggerFactory.getLogger(VacancyServiceImpl.class);
    /**
     * Метод позволяет получить вакансию по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id вакансии
     * @return - найденная вакансия
     */
    @Override
    public VacancyDto getVacancyById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска вакансии по id");
        VacancyEntity vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено вакансии"));
        return vacancyMapper.mapToVacancyDto(vacancy);
    }

    /**
     * Метод позволяет получить все вакансии
     * @throws EntitiesNotFoundException - в случае если ни оной вакансии не найдено
     * @return - список подкатегорий
     */
    @Override
    public List<VacancyDto> getAllVacancies() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех вакансий");
        List<VacancyEntity> vacancies = vacancyRepository.findAll();
        if(vacancies.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной вакансии");
        return vacancyListMapper.mapToVacancyDtoList(vacancies);
    }

    /**
     * Метод позволяет сохранить вакансию
     * @param vacancyForSaveDto - вакансия для сохранения
     * @return - сохраненная вакансия
     */
    @Override
    @Transactional
    public VacancyDto saveVacancy(VacancyForSaveDto vacancyForSaveDto) {
        logger.info("Попытка сохранения вакансии");
        VacancyEntity vacancy = vacancyMapper.mapToVacancyEntity(vacancyForSaveDto);
        VacancyEntity savedVacancy = vacancyRepository.save(vacancy);
        return vacancyMapper.mapToVacancyDto(savedVacancy);
    }

    /**
     * Метод позволят редактировать вакансию
     * @param vacancyForUpdateDto - вакансия для редактирования
     * @throws EntityNotFoundException - в случае если не найдена вакансия для редактирования
     * @return - отредактированная вакансия
     */
    @Override
    @Transactional
    public VacancyDto updateVacancy(VacancyForUpdateDto vacancyForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования вакансии");
        logger.info("Поиск вакансии по id");
        VacancyEntity vacancy = vacancyRepository.findById(vacancyForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено вакансии"));
        vacancyMapper.updateVacancyEntity(vacancyForUpdateDto, vacancy);
        VacancyEntity updatedVacancy = vacancyRepository.save(vacancy);
        return vacancyMapper.mapToVacancyDto(updatedVacancy);
    }

    /**
     * Метод позволяет удалять вакансию
     * @param id - id подкатегории
     * @return - удалена или нет
     */
    @Override
    @Transactional
    public boolean deleteVacancyById(Integer id) {
        logger.info("Попытка удаления вакансии");
        vacancyRepository.deleteById(id);
        return !vacancyRepository.existsById(id);
    }
}
