package ru.yandex.practicum.collector.handler.hub;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerHubEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.collector.model.hub.events.EventDeviceRemoved;
import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@HandlerHubEvent(HubEventType.DEVICE_REMOVED)
@Component
public class HubDeviceRemovedEventHandler extends HubEventHandler<DeviceRemovedEventAvro> {

    public HubDeviceRemovedEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected DeviceRemovedEventAvro mapToAvro(HubEvent event) {
        EventDeviceRemoved hubEvent = (EventDeviceRemoved) event;

        return DeviceRemovedEventAvro.newBuilder()
                .setId(hubEvent.getId())
                .build();
    }
}
