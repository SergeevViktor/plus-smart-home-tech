package ru.yandex.practicum.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerSensorEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.sensor.enums.SensorEventType;
import ru.yandex.practicum.collector.model.sensor.events.MotionSensorEvent;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@HandlerSensorEvent(SensorEventType.MOTION_SENSOR_EVENT)
@Component
public class MotionSensorEventHandler extends SensorEventHandler<MotionSensorAvro> {

    public MotionSensorEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected MotionSensorAvro mapToAvro(SensorEvent event) {
        MotionSensorEvent sensorEvent = (MotionSensorEvent) event;

        return MotionSensorAvro.newBuilder()
                .setLinkQuality(sensorEvent.getLinkQuality())
                .setMotion(sensorEvent.getMotion())
                .setVoltage(sensorEvent.getVoltage())
                .build();
    }
}
