package kg.zavod.Tare.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.color.HighlightingCompositeConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Класс для подсветки логов в консоли. К нему обращается logback.xml
 */
public class CustomerHighlightConverter  extends HighlightingCompositeConverter {
    /**
     * Метод для подсветки логов
     * @param event - объект события
     * @return - цвет для подсветки
     */
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        if (level != null) {
            switch (level.toInt()) {
                case Level.INFO_INT:
                    return "87";
                case Level.WARN_INT:
                    return "93";
                case Level.ERROR_INT:
                    return "91";
            }
        }
        return null;
    }
}
