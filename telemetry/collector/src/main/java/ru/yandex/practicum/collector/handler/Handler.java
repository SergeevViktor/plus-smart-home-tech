package ru.yandex.practicum.collector.handler;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.annotation.HandlerHubEvent;
import ru.yandex.practicum.collector.annotation.HandlerSensorEvent;
import ru.yandex.practicum.collector.handler.hub.HubEventHandler;
import ru.yandex.practicum.collector.handler.sensor.SensorEventHandler;
import ru.yandex.practicum.collector.model.hub.enums.HubEventType;
import ru.yandex.practicum.collector.model.sensor.enums.SensorEventType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Handler {
    private final BeanSorter beanSorter;
    @Getter
    private Map<Enum<?>, HubEventHandler<? extends SpecificRecordBase>> hubEventHandlers = new HashMap<>();
    @Getter
    private Map<Enum<?>, SensorEventHandler<? extends SpecificRecordBase>> sensorEventHandlers = new HashMap<>();

    @PostConstruct
    public void init() {
        hubEventHandlers = beanSorter.getAnnotatedBeans(HandlerHubEvent.class)
                .stream()
                .map(h -> (HubEventHandler<?>) h)
                .collect(Collectors.toMap(
                        Handler::getValueHub, h -> h)
                );

        sensorEventHandlers = beanSorter.getAnnotatedBeans(HandlerSensorEvent.class)
                .stream()
                .map(h -> (SensorEventHandler<?>) h)
                .collect(Collectors.toMap(
                        Handler::getValueSensor, h -> h)
                );
    }

    private static HubEventType getValueHub(HubEventHandler<?> handler) {
        HandlerHubEvent handlerAnnotation = handler.getClass().getAnnotation(HandlerHubEvent.class);

        if (handlerAnnotation == null) {
            throw new IllegalArgumentException("No annotation found for " + handler.getClass().getName());
        }
        return handlerAnnotation.value();
    }

    private static SensorEventType getValueSensor(SensorEventHandler<?> handler) {
        HandlerSensorEvent handlerAnnotation = handler.getClass().getAnnotation(HandlerSensorEvent.class);

        if (handlerAnnotation == null) {
            throw new IllegalArgumentException("No annotation found for " + handler.getClass().getName());
        }
        return handlerAnnotation.value();
    }
}
