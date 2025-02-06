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
     *
     * @param id - id партнера
     */
    void deletePartnerById(Integer id);
}
