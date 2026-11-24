package ru.yandex.practicum.collector.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.sensor.events.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
@RequiredArgsConstructor
public abstract class SensorEventHandler<T extends SpecificRecordBase> {
    private final Producer producer;
    private static final String SENSOR_TOPIC = "telemetry.sensors.v1";

    protected abstract T mapToAvro(SensorEvent event);

    public void handle(SensorEvent event) {
        T avro = mapToAvro(event);

        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(avro)
                .build();

        producer.send(sensorEventAvro, event.getHubId(), event.getTimestamp(), SENSOR_TOPIC);
    }
}
