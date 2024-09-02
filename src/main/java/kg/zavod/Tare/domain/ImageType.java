package kg.zavod.Tare.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import lombok.Getter;

import java.util.Optional;

@Getter
@Schema(description = "Описание типов картинок которое поддерживает приложение")
public enum ImageType {
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp"),
    TIFF("tiff"),
    SVG("svg"),
    WEBP("webp"),
    JFIF("jfif");

    private final String format;

    ImageType(String format) {
        this.format = format;
    }

    public static ImageType findImageTypeByFormat(String format) throws EntityNotFoundException {
        for(ImageType imageType : ImageType.values()){
            if(imageType.getFormat().equals(format)){
                return imageType;
            }
        }
        throw new EntityNotFoundException("Формат не поддерживается приложением");
    }

    /**
     * Метод принимает строку с содержанием типа контента и возвращает формат
     * @param contentType - типа контента
     * @return - формат
     */
    public static Optional<ImageType> fromContentType(String contentType) {
        for (ImageType imageType : values()) {
            if (contentType.toLowerCase().contains(imageType.getFormat())) {
                return Optional.of(imageType);
            }
        }
        return Optional.empty();
    }

}
