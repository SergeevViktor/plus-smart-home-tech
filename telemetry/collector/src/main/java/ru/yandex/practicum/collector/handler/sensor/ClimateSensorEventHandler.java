package ru.yandex.practicum.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerSensorEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.sensor.enums.SensorEventType;
import ru.yandex.practicum.collector.model.sensor.events.ClimateSensorEvent;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@HandlerSensorEvent(SensorEventType.CLIMATE_SENSOR_EVENT)
@Component
public class ClimateSensorEventHandler extends SensorEventHandler<ClimateSensorAvro> {

    public ClimateSensorEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected ClimateSensorAvro mapToAvro(SensorEvent event) {
        ClimateSensorEvent sensorEvent = (ClimateSensorEvent) event;

        return ClimateSensorAvro.newBuilder()
                .setTemperatureC(sensorEvent.getTemperatureC())
                .setHumidity(sensorEvent.getHumidity())
                .setCo2Level(sensorEvent.getCo2Level())
                .build();
    }
}
