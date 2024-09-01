package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.certificate.CertificateDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface CertificateService {
    /**
     * Метод позволяет получить сертификат по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id сертификата
     * @return - найденный сертификат
     */
    CertificateDto getCertificateById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все сертификаты
     * @throws EntitiesNotFoundException - в случае если ни оного сертификата не найдено
     * @return - список сертификатов
     */
    List<CertificateDto> getAllCertificate() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить сертификат
     * @param certificateForSaveDto - сертификат для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @return - сохраненный сертификат
     */
    CertificateDto saveCertificate(CertificateForSaveDto certificateForSaveDto) throws EntityNotFoundException;

    /**
     * Метод позволят редактировать сертификат
     * @param certificateForUpdateDto - сертификат для редактирования
     * @throws EntityNotFoundException - в случае если не найден сертификат для редактирования
     * @return - отредактированный сертификат
     */
    CertificateDto updateCertificate(CertificateForUpdateDto certificateForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять сертификат
     * @param id - id сертификата
     * @return - удален или нет
     */
    boolean deleteCertificateById(Integer id);
}
