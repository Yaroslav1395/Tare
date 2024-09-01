package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateDto;

import java.util.List;


public interface PartnerService {
    /**
     * Метод позволяет получить партнера по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id партнера
     * @return - найденный партнер
     */
    PartnerDto getPartnerById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить всех партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного партнера не найдено
     * @return - список партнеров
     */
    List<PartnerDto> getAllPartners() throws EntitiesNotFoundException;

    /**
     * Метод позволяет получить все активных партнеров
     * @return - список партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного активного партнера не найдено
     */
    List<PartnerDto> getAllActivePartners() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить партнера
     * @param partnerForSaveDto - партнер для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @return - сохраненный партнер
     */
    PartnerDto savePartner(PartnerForSaveDto partnerForSaveDto) throws EntityNotFoundException;

    /**
     * Метод позволят редактировать партнера
     * @param partnerForUpdateDto - партнер для редактирования
     * @throws EntityNotFoundException - в случае если не найден партнер для редактирования
     * @return - отредактированный партнер
     */
    PartnerDto updatePartner(PartnerForUpdateDto partnerForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять партнера
     * @param id - id партнера
     * @return - удален или нет
     */
    boolean deletePartnerById(Integer id);

    /**
     * Метод позволяет изменять активность партнера
     * @param id - id партнера активность которого нужно изменить
     * @param isActive - флаг активности
     * @return - текущее состояние партнера
     */
    boolean changePartnerActivityById(Integer id, Boolean isActive);
}
