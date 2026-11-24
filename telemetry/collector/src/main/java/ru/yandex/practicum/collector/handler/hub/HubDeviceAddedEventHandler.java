package ru.yandex.practicum.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerHubEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.collector.model.hub.events.EventDeviceAdded;
import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;


@HandlerHubEvent(HubEventType.DEVICE_ADDED)
@Component
public class HubDeviceAddedEventHandler extends HubEventHandler<DeviceAddedEventAvro> {

    public HubDeviceAddedEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected DeviceAddedEventAvro mapToAvro(HubEvent event) {
        EventDeviceAdded hubEvent = (EventDeviceAdded) event;

        return DeviceAddedEventAvro.newBuilder()
                .setId(hubEvent.getId())
                .setType(DeviceTypeAvro.valueOf(hubEvent.getDeviceType().name()))
                .build();
    }
}
