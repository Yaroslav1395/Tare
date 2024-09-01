package kg.zavod.Tare.dto.state;

import lombok.Getter;

@Getter
public enum ResponseState {
    SUCCESS("Успешно"),
    INFO("Информационная"),
    FAIL("Неуспешно"),
    ERROR("Произошла ошибка"),
    DUPLICATE("Дубликат в настройке");

    private final String description;

    ResponseState(String description) {
        this.description = description;
    }

}
