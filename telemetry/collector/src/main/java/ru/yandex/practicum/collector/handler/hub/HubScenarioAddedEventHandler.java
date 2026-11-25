package ru.yandex.practicum.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerHubEvent;
import ru.yandex.practicum.collector.config.Producer;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.collector.model.hub.events.EventScenarioAdded;
import ru.yandex.practicum.collector.model.hub.events.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;

import java.util.List;

@HandlerHubEvent(HubEventType.SCENARIO_ADDED)
@Component
public class HubScenarioAddedEventHandler extends HubEventHandler<ScenarioAddedEventAvro> {

    public HubScenarioAddedEventHandler(Producer producer) {
        super(producer);
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEvent event) {
        EventScenarioAdded hubEvent = (EventScenarioAdded) event;

        return ScenarioAddedEventAvro.newBuilder()
                .setName(hubEvent.getName())
                .setActions(deviceActionAvro(hubEvent))
                .setConditions(getScenarioConditionAvro(hubEvent))
                .build();
    }

    private List<DeviceActionAvro> deviceActionAvro(EventScenarioAdded event) {
        return event.getActions().stream()
                .map(action -> DeviceActionAvro.newBuilder()
                        .setSensorId(action.getSensorId())
                        .setType(ActionTypeAvro.valueOf(action.getType().name()))
                        .setValue(action.getValue())
                        .build())
                .toList();
    }

    private List<ScenarioConditionAvro> getScenarioConditionAvro(EventScenarioAdded event) {
        return event.getConditions().stream()
                .map(condition -> ScenarioConditionAvro.newBuilder()
                        .setSensorId(condition.getSensorId())
                        .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                        .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                        .setValue(condition.getValue())
                        .build())
                .toList();
    }
}
