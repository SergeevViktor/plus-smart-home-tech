package ru.yandex.practicum.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerHubEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.collector.model.hub.events.EventScenarioRemoved;
import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@HandlerHubEvent(HubEventType.SCENARIO_REMOVED)
@Component
public class HubScenarioRemovedEventHandler extends HubEventHandler<ScenarioRemovedEventAvro> {

    public HubScenarioRemovedEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected ScenarioRemovedEventAvro mapToAvro(HubEvent event) {
        EventScenarioRemoved removedEvent = (EventScenarioRemoved) event;

        return ScenarioRemovedEventAvro.newBuilder()
                .setName(removedEvent.getName())
                .build();
    }
}
