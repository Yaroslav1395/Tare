package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForAdminDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface DistrictService {
    /**
     * Метод позволяет получить все районы
     * @throws EntitiesNotFoundException - в случае если ни оного района не найдено
     * @return - список районов
     */
    List<DistrictForAdminDto> getAllDistrictsForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить район
     * @param districtForSaveDto - район для сохранения
     * @throws EntityNotFoundException - в случае если не найдено территориального деления для района по id
     * @throws DuplicateEntityException - в случае если район с таким именем уже есть в территориальном делении
     */
    void saveDistrict(DistrictForSaveAdminDto districtForSaveDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволят редактировать район
     * @param districtForUpdateDto - район для редактирования
     * @throws EntityNotFoundException - в случае если не найден район для редактирования
     * @throws DuplicateEntityException - в случае если район с таким именем уже есть в территориальном делении
     */
    void updateDistrict(DistrictForUpdateAdminDto districtForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалить район
     * @param id - id района
     */
    void deleteDistrictById(Integer id);
}
