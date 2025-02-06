package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.certificate.CertificateForAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUserDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CertificateService {

    /**
     * Метод позволяет получить все сертификаты для клиента
     * @return - список сертификатов
     */
    List<CertificateForUserDto> getAllCertificateForUser();

    /**
     * Метод позволяет получить все сертификаты для админки
     * @throws EntitiesNotFoundException - в случае если ни оного сертификата не найдено
     * @return - список сертификатов
     */
    List<CertificateForAdminDto> getAllCertificateForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить сертификат
     * @param certificateForSaveAdminDto - сертификат для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @throws DuplicateEntityException - в случае если дублируется название сертификата
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    void saveCertificate(CertificateForSaveAdminDto certificateForSaveAdminDto) throws EntityNotFoundException, DuplicateEntityException, IOException;

    /**
     * Метод позволят редактировать сертификат
     * @param certificateForUpdateAdminDto - сертификат для редактирования
     * @throws EntityNotFoundException - в случае если не найден сертификат для редактирования
     * @throws DuplicateEntityException - в случае если сертификат с таким именем уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    void updateCertificate(CertificateForUpdateAdminDto certificateForUpdateAdminDto) throws EntityNotFoundException, DuplicateEntityException, IOException;

    /**
     * Метод позволяет удалять сертификат
     *
     * @param id - id сертификата
     */
    void deleteCertificateById(Integer id);
}
