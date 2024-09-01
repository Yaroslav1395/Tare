package kg.zavod.Tare.service.util;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public class UtilService {
    /**
     * Метод позволяет получить формат картинки из MultipartFile
     * @param file - картинка
     * @return - формат картинки
     * @throws EntityNotFoundException - в случае если приложение не поддерживает формат
     */
    public static ImageType getImageTypeFrom(MultipartFile file) throws EntityNotFoundException {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return ImageType.findImageTypeByFormat(fileExtension);
    }
}
