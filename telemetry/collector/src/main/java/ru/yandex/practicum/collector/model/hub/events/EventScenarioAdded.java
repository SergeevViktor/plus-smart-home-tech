package ru.yandex.practicum.collector.model.hub.events;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.hub.DeviceAction;
import ru.yandex.practicum.collector.model.hub.ScenarioCondition;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class EventScenarioAdded extends HubEvent {
    @NotBlank
    @Size(min = 3, message = "Имя сценария не может быть менее трех символов")
    private String name;
    @NotEmpty
    @Valid
    private List<ScenarioCondition> conditions;
    @NotEmpty
    @Valid
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
