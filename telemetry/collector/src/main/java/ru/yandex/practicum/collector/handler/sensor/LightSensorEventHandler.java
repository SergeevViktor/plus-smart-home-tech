package ru.yandex.practicum.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerSensorEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.sensor.enums.SensorEventType;
import ru.yandex.practicum.collector.model.sensor.events.LightSensorEvent;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@HandlerSensorEvent(SensorEventType.LIGHT_SENSOR_EVENT)
@Component
public class LightSensorEventHandler extends SensorEventHandler<LightSensorAvro> {

    public LightSensorEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected LightSensorAvro mapToAvro(SensorEvent event) {
        LightSensorEvent sensorEvent = (LightSensorEvent) event;

        return LightSensorAvro.newBuilder()
                .setLinkQuality(sensorEvent.getLinkQuality())
                .setLuminosity(sensorEvent.getLuminosity())
                .build();
    }

}
