package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerForAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUserDto;

import java.io.IOException;
import java.util.List;


public interface PartnerService {
    /**
     * Метод позволяет получить список партнеров для клиента
     * @return - список партнеров
     */
    List<PartnerForUserDto> getAllPartnersForUser();

    /**
     * Метод позволяет получить всех партнеров для админки
     * @throws EntitiesNotFoundException - в случае если ни одного партнера не найдено
     * @return - список партнеров
     */
    List<PartnerForAdminDto> getAllPartners() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить партнера. Используется в админке.
     * @param partnerForSaveAdminDto - партнер для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     */
    void savePartner(PartnerForSaveAdminDto partnerForSaveAdminDto) throws EntityNotFoundException, IOException;

    /**
     * Метод позволят редактировать партнера
     * @param partnerForUpdateAdminDto - партнер для редактирования
     * @throws EntityNotFoundException - в случае если не найден партнер для редактирования
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    void updatePartner(PartnerForUpdateAdminDto partnerForUpdateAdminDto) throws EntityNotFoundException, IOException;

    /**
     * Метод позволяет удалять партнера
     * @param id - id партнера
     * @return - удален или нет
     */
    boolean deletePartnerById(Integer id);









    /**
     * Метод позволяет получить партнера по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id партнера
     * @return - найденный партнер
     */
    PartnerForAdminDto getPartnerById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все активных партнеров
     * @return - список партнеров
     * @throws EntitiesNotFoundException - в случае если ни одного активного партнера не найдено
     */
    List<PartnerForAdminDto> getAllActivePartners() throws EntitiesNotFoundException;

    /**
     * Метод позволяет изменять активность партнера
     * @param id - id партнера активность которого нужно изменить
     * @param isActive - флаг активности
     * @return - текущее состояние партнера
     */
    boolean changePartnerActivityById(Integer id, Boolean isActive);
}
