package ru.astepanova.lib.fakegenerator.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.NonNull;
import ru.astepanova.lib.fakegenerator.FakeHelper;

/**
 * Класс для создания экземпляра класса и заполнения его полей рандомными
 * значениями.
 * Комментарий: ожидается класс, имеющий конструктор с параметрами
 *
 * @author astepanova
 */
public class ConstructorFakeGenerator extends FakeGenerator {

    @Override
    public <T> T createFaked(@NonNull Class<T> clazz, @NonNull FakeHelper fakeHelper) {
        try {
            final Field[] fields = clazz.getDeclaredFields();
            final Constructor<?> maxParameterConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                    .max(Comparator.comparingInt(o -> o.getParameterTypes().length))
                    .orElseThrow(() -> new RuntimeException("No Constructor found"));
            final Map<String, Class<?>> constructorArgs = Arrays.stream(maxParameterConstructor.getParameters())
                    .collect(Collectors.toMap(p -> p.getName(), v -> v.getType()));
            final ArrayList<Object> constructorArguments = new ArrayList<>();
            for (var field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (constructorArgs.entrySet().stream()
                        .anyMatch(a -> a.getValue().isAssignableFrom(field.getType()))) {
                    constructorArguments.add(parameterValue(field, fakeHelper));
                }
            }
            // noinspection unchecked
            return (T) maxParameterConstructor.newInstance(constructorArguments.toArray());
        } catch (SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            String msg = "Cannot instantiate and fill class " + clazz;
            LOGGER.warning(msg);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
