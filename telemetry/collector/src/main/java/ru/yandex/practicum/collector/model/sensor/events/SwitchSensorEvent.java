package ru.yandex.practicum.collector.model.sensor.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.model.sensor.enums.SensorEventType;

@Setter
@Getter
@ToString(callSuper = true)
public class SwitchSensorEvent extends SensorEvent {
    private Boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
