package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForSaveDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.delivery.district.DistrictListMapper;
import kg.zavod.Tare.mapper.delivery.district.DistrictMapper;
import kg.zavod.Tare.repository.delivery.DistrictRepository;
import kg.zavod.Tare.repository.delivery.DivisionRepository;
import kg.zavod.Tare.service.delivery.DistrictService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictMapper districtMapper;
    private final DistrictListMapper districtListMapper;
    private static final Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);
    /**
     * Метод позволяет получить район по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id района
     * @return - найденный район
     */
    @Override
    public DistrictDto getDistrictById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска района по id");
        DistrictEntity district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено района"));
        return districtMapper.mapToDistrictDto(district);
    }

    /**
     * Метод позволяет получить все районы
     * @throws EntitiesNotFoundException - в случае если ни оного района не найдено
     * @return - список районов
     */
    @Override
    public List<DistrictDto> getAllDistricts() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех районов");
        List<DistrictEntity> districts = districtRepository.findAll();
        if(districts.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного района");
        return districtListMapper.mapToDistrictDtoList(districts);
    }

    /**
     * Метод позволяет сохранить район
     * @param districtForSaveDto - район для сохранения
     * @return - сохраненный район
     * @throws EntityNotFoundException - в случае если не найдено территориального деления для района по id
     * @throws DuplicateEntityException - в случае если район с таким именем уже есть в территориальном делении
     */
    @Override
    public DistrictDto saveDistrict(DistrictForSaveDto districtForSaveDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка сохранения района");
        boolean isDuplicate = districtRepository.findByNameAndDivisionId(districtForSaveDto.getName(), districtForSaveDto.getDivisionId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Район с таким именем уже существует в территориальном делении");
        logger.info("Поиск территориального деления для района по id");
        DivisionEntity division = divisionRepository.findById(districtForSaveDto.getDivisionId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено территориального деления для района по id"));
        DistrictEntity district = districtMapper.mapToDistrictEntity(districtForSaveDto, division);
        DistrictEntity savedDistrict = districtRepository.save(district);
        return districtMapper.mapToDistrictDto(savedDistrict);
    }

    /**
     * Метод позволят редактировать район
     * @param districtForUpdateDto - район для редактирования
     * @throws EntityNotFoundException - в случае если не найден район для редактирования
     * @throws DuplicateEntityException - в случае если район с таким именем уже есть в территориальном делении
     * @return - отредактированный район
     */
    @Override
    public DistrictDto updateDistrict(DistrictForUpdateDto districtForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования района");
        logger.info("Поиск района по id");
        boolean isDuplicate = districtRepository.findByNameAndDivisionIdAndIdIsNot(districtForUpdateDto.getName(),
                districtForUpdateDto.getDivisionId(), districtForUpdateDto.getId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Район с таким именем уже существует в территориальном делении");
        DistrictEntity district = districtRepository.findById(districtForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено района по id"));
        logger.info("Поиск территориального деления по id");
        DivisionEntity division = divisionRepository.findById(districtForUpdateDto.getDivisionId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено территориального деления"));
        districtMapper.updateDistrictEntity(districtForUpdateDto, division, district);
        DistrictEntity updatedDistrict = districtRepository.save(district);
        return districtMapper.mapToDistrictDto(updatedDistrict);
    }

    /**
     * Метод позволяет удалить район
     * @param id - id района
     * @return - удален или нет
     */
    @Override
    public boolean deleteDistrictById(Integer id) {
        logger.info("Попытка удаления района");
        districtRepository.deleteById(id);
        return !districtRepository.existsById(id);
    }
}
