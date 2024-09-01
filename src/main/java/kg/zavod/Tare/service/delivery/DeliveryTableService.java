package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.dto.deliviry.DeliveryTable;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;

public interface DeliveryTableService {
    /**
     * Метод позволяет получить данные для отображения таблицы доставки
     * @return - данные для отображения таблицы доставки
     * @throws EntitiesNotFoundException - в случае если не будут найдены территориальные деления
     * или допустимые объемы доставки
     */
    DeliveryTable getDeliveryTable() throws EntitiesNotFoundException;
}
