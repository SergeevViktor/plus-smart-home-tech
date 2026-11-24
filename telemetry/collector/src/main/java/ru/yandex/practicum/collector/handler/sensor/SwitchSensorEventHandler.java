package ru.yandex.practicum.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerSensorEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.sensor.enums.SensorEventType;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;
import ru.yandex.practicum.collector.model.sensor.events.SwitchSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@HandlerSensorEvent(SensorEventType.SWITCH_SENSOR_EVENT)
@Component
public class SwitchSensorEventHandler extends SensorEventHandler<SwitchSensorAvro> {

    public SwitchSensorEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected SwitchSensorAvro mapToAvro(SensorEvent event) {
        SwitchSensorEvent sensorEvent = (SwitchSensorEvent) event;

        return SwitchSensorAvro.newBuilder()
                .setState(sensorEvent.getState())
                .build();
    }
}
