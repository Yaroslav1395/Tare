package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeForAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUserDto;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    /**
     * Метод позволяет получить все новости для клиента
     * @return - список новостей
     */
    List<NoticeForUserDto> getAllNoticesForUser();

    /**
     * Метод позволяет получить все новости
     * @return - список новостей
     */
    List<NoticeForAdminDto> getAllNoticesForAdmin();

    /**
     * Метод позволяет сохранить новость
     * @param noticeForSaveAdminDto - новость для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @throws IOException - в случае если невозможно сохранить картинку
     */
    void saveNotice(NoticeForSaveAdminDto noticeForSaveAdminDto) throws EntityNotFoundException, IOException;

    /**
     * Метод позволят редактировать новость
     * @param noticeForUpdateAdminDto - новость для редактирования
     * @throws EntityNotFoundException - в случае если не найдена новость для редактирования
     * @throws IOException - в случае если невозможно сохранить картинку
     */
    void updateNotice(NoticeForUpdateAdminDto noticeForUpdateAdminDto) throws EntityNotFoundException, IOException;

    /**
     * Метод позволяет удалять новость
     *
     * @param id - id новости
     */
    void deleteNoticeById(Integer id);
}
