package ru.yandex.practicum.collector.service;

import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;

public interface EventService {

    void addHubEvent(HubEvent hubEvent);

    void addSensorEvent(SensorEvent sensorEvent);
}
