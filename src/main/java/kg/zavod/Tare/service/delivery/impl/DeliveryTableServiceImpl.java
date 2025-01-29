package kg.zavod.Tare.service.delivery.impl;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.DeliveryTable;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForAdminDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForTableDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionTableDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.mapper.delivery.capacity.CapacityListMapper;
import kg.zavod.Tare.mapper.delivery.district.DistrictMapper;
import kg.zavod.Tare.mapper.delivery.price.DeliveryPriceMapper;
import kg.zavod.Tare.repository.delivery.CapacityRepository;
import kg.zavod.Tare.repository.delivery.DivisionRepository;
import kg.zavod.Tare.service.delivery.DeliveryTableService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryTableServiceImpl implements DeliveryTableService {
    private final CapacityRepository capacityRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictMapper districtMapper;
    private final CapacityListMapper capacityListMapper;
    private final DeliveryPriceMapper deliveryPriceMapper;
    private static final Logger logger = LoggerFactory.getLogger(DivisionServiceImpl.class);

    /**
     * Метод позволяет получить данные для отображения таблицы доставки
     * @return - данные для отображения таблицы доставки
     * @throws EntitiesNotFoundException - в случае если не будут найдены территориальные деления
     * или допустимые объемы доставки
     */
    @Override
    @Transactional(readOnly = true)
    public DeliveryTable getDeliveryTable() throws EntitiesNotFoundException {
        logger.info("Попытка сбора данных для отображения в таблице");
        logger.info("Поиск допустимых объемов");
        List<CapacityEntity> capacities = capacityRepository.findAll();
        if(capacities.isEmpty()) throw new EntitiesNotFoundException("Не найдены допустимые объемы доставки");
        capacities.sort(Comparator.comparing(CapacityEntity::getCapacityFrom));
        List<CapacityForAdminDto> capacitiesDto = capacityListMapper.mapToCapacityForAdminDtoList(capacities);
        logger.info("Поиск территориальных делений");
        List<DivisionEntity> divisions = divisionRepository.findAll();
        if(capacities.isEmpty()) throw new EntitiesNotFoundException("Не найдены территориальные деления доставки");
        List<DivisionTableDto> divisionTable = mapToDivisionTableDto(divisions);
        return DeliveryTable.buildDeliveryTable(divisionTable, capacitiesDto);
    }

    /**
     * Метод принимает список территориальных делений. Для каждого деления получает список районов. Для каждого
     * района получает список цен доставки и преобразует этот список в словарь, где ключом является id
     * допустимого объема, а значением объект цены доставки
     * @param divisions - список территориальных делений
     * @return - список преобразованных территориальных делений для отображения в таблице
     */
    public List<DivisionTableDto> mapToDivisionTableDto(List<DivisionEntity> divisions) {
        logger.info("Преобразование территориальных делений для отображения в таблице");
        return divisions.stream()
                .map(division -> {
                    List<DistrictForTableDto> districts = division.getDistricts().stream()
                            .map(this::mapToDistrictForTable)
                            .collect(Collectors.toList());
                    return DivisionTableDto.buildDivisionTableDto(division, districts);
                })
                .collect(Collectors.toList());
    }

    /**
     * Метод принимает район и преобразовывает его в район для отображения в таблице, путем создания
     * словаря, где значением является id допустимого объема, а значением объект цены доставки
     * @param district - район доставки
     * @return - преобразованный район для отображения в таблице
     */
    private DistrictForTableDto mapToDistrictForTable(DistrictEntity district) {
        logger.info("Преобразование района для отображения в таблице");
        Map<Integer, DeliveryPriceForUpdateDto> capacityPriceMap =  district.getDistrictCapacities().stream()
                .collect(Collectors.toMap(
                        price -> price.getCapacity().getId(),
                        deliveryPriceMapper::mapToDeliveryPriceForUpdateDto));
        return districtMapper.mapToDistrictForTableDto(district, capacityPriceMap);
    }
}
