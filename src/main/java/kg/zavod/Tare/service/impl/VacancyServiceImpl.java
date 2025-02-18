package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.vacancy.VacancyForAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUserDto;
import kg.zavod.Tare.mapper.vacancy.VacancyListMapper;
import kg.zavod.Tare.mapper.vacancy.VacancyMapper;
import kg.zavod.Tare.repository.VacancyRepository;
import kg.zavod.Tare.service.VacancyService;
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
     * Метод позволяет получить все вакансии
     * @return - список подкатегорий
     */
    @Override
    public List<VacancyForAdminDto> getAllVacanciesForAdmin() {
        logger.info("Попытка поиска всех вакансий");
        List<VacancyEntity> vacancies = vacancyRepository.findAll();
        return vacancyListMapper.mapToVacancyDtoList(vacancies);
    }

    /**
     * Метод позволяет получить все вакансии для клиента
     * @return - список подкатегорий
     */
    @Override
    public List<VacancyForUserDto> getAllVacanciesForUser() {
        logger.info("Попытка поиска всех вакансий для клиента");
        List<VacancyEntity> vacancies = vacancyRepository.findAll();
        return vacancyListMapper.mapToVacancyForUserDtoList(vacancies);
    }

    /**
     * Метод позволяет сохранить вакансию
     * @param vacancyForSaveAdminDto - вакансия для сохранения
     */
    @Override
    @Transactional
    public void saveVacancy(VacancyForSaveAdminDto vacancyForSaveAdminDto) {
        logger.info("Попытка сохранения вакансии");
        VacancyEntity vacancy = vacancyMapper.mapToVacancyEntity(vacancyForSaveAdminDto);
        vacancyRepository.save(vacancy);
    }

    /**
     * Метод позволят редактировать вакансию
     * @param vacancyForUpdateAdminDto - вакансия для редактирования
     * @throws EntityNotFoundException - в случае если не найдена вакансия для редактирования
     */
    @Override
    @Transactional
    public void updateVacancy(VacancyForUpdateAdminDto vacancyForUpdateAdminDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования вакансии");
        logger.info("Поиск вакансии по id");
        VacancyEntity vacancy = vacancyRepository.findById(vacancyForUpdateAdminDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено вакансии"));
        vacancyMapper.updateVacancyEntity(vacancyForUpdateAdminDto, vacancy);
        vacancyRepository.save(vacancy);
    }

    /**
     * Метод позволяет удалять вакансию
     *
     * @param id - id подкатегории
     */
    @Override
    @Transactional
    public void deleteVacancyById(Integer id) {
        logger.info("Попытка удаления вакансии");
        vacancyRepository.deleteById(id);
        vacancyRepository.existsById(id);
    }
}
