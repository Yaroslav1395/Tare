package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForSaveDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface DistrictService {
    /**
     * Метод позволяет получить район по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id района
     * @return - найденный район
     */
    DistrictDto getDistrictById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все районы
     * @throws EntitiesNotFoundException - в случае если ни оного района не найдено
     * @return - список районов
     */
    List<DistrictDto> getAllDistricts() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить район
     * @param districtForSaveDto - район для сохранения
     * @return - сохраненный район
     * @throws EntityNotFoundException - в случае если не найдено территориального деления для района по id
     */
    DistrictDto saveDistrict(DistrictForSaveDto districtForSaveDto) throws EntityNotFoundException;

    /**
     * Метод позволят редактировать район
     * @param districtForUpdateDto - район для редактирования
     * @throws EntityNotFoundException - в случае если не найден район для редактирования
     * @return - отредактированный район
     */
    DistrictDto updateDistrict(DistrictForUpdateDto districtForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалить район
     * @param id - id района
     * @return - удален или нет
     */
    boolean deleteDistrictById(Integer id);
}
