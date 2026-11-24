package ru.yandex.practicum.collector.handler.hub;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
@RequiredArgsConstructor
public abstract class HubEventHandler<T extends SpecificRecordBase> {
    private final Producer producer;
    private static final String HUB_TOPIC = "telemetry.hubs.v1";

    protected abstract T mapToAvro(HubEvent event);

    public void handle(HubEvent event) {
        T avro = mapToAvro(event);

        HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(avro)
                .build();

        producer.send(hubEventAvro, event.getHubId(), event.getTimestamp(), HUB_TOPIC);
    }
}
