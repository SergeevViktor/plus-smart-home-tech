package ru.yandex.practicum.collector.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.handler.Handler;
import ru.yandex.practicum.collector.handler.hub.HubEventHandler;
import ru.yandex.practicum.collector.handler.sensor.SensorEventHandler;
import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;

@Service
@RequiredArgsConstructor
public class EventServiceManager implements EventService {
    private final Handler handler;

    @Override
    public void addHubEvent(HubEvent hubEvent) {
        HubEventHandler<? extends SpecificRecordBase> hubEventHandler = handler
                .getHubEventHandlers().get(hubEvent.getType());
        if (hubEventHandler != null) {
            hubEventHandler.handle(hubEvent);
        }
        else {
            throw new RuntimeException("Не найден тип: " + hubEvent.getType());
        }
    }

    @Override
    public void addSensorEvent(SensorEvent sensorEvent) {
        SensorEventHandler<? extends SpecificRecordBase> sensorEventHandler = handler
                .getSensorEventHandlers().get(sensorEvent.getType());
        if (sensorEventHandler != null) {
            sensorEventHandler.handle(sensorEvent);
        }
        else {
            throw new RuntimeException("Не найден тип: " + sensorEvent.getType());
        }
    }
}
