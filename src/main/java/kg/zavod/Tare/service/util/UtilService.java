package kg.zavod.Tare.service.util;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    /**
     * Метод позволяет сохранить картинку в указанную папку
     * @param file - картинка для сохранения
     * @param folder - папка для сохранения
     * @param basicPath - путь для сохранения
     * @return - путь к сохраненной картинке
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    public static String saveImage(MultipartFile file, String folder, String basicPath) throws IOException {
        String uploadDir = basicPath + folder;
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            boolean created = uploadPath.mkdirs();
            if (!created) {
                throw new IOException("Не удалось создать директорию для загрузки файлов: " + uploadDir);
            }
        }
        if (file.isEmpty()) {
            throw new IOException("Файл отсутствует или пустой");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        File newfile = new File(uploadPath, uniqueFilename);
        file.transferTo(newfile);
        return folder + "/" + uniqueFilename;
    }
}
