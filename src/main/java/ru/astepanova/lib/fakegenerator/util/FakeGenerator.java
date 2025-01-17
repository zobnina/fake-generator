package ru.astepanova.lib.fakegenerator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import lombok.NonNull;
import ru.astepanova.lib.fakegenerator.FakeHelper;

public abstract class FakeGenerator {

    protected static final Logger LOGGER = Logger.getLogger(FakeGenerator.class.getName());

    abstract <T> T createFaked(@NonNull final Class<T> clazz, @NonNull final FakeHelper fakeHelper);

    public <T> T createFaked(@NonNull final Class<T> clazz) {
        return createFaked(clazz, FakeHelper.builder().build());
    }

    protected Object parameterValue(Method fieldMethod, Field field, FakeHelper fakeHelper) {
        Class<?>[] parameterTypes = fieldMethod.getParameterTypes();
        assert parameterTypes.length == 1;

        return parameterValue(field, fakeHelper);
    }

    protected Object parameterValue(Field field, FakeHelper fakeHelper) {
        Object parameterValue = null;
        if (!skip(field, fakeHelper)) {
            if (fakeHelper.getFieldValueMap().containsKey(field.getName())) {
                parameterValue = fakeHelper.getFieldValueMap().get(field.getName());
            } else {
                parameterValue = fakeHelper.getFieldValues().stream()
                        .filter(v -> v.getClass().equals(field.getType()))
                        .findFirst()
                        .orElseGet(() -> RandomGeneratorUtil.randomValue(field.getType()));
            }
        }
        return parameterValue;
    }

    protected boolean skip(Field field, FakeHelper fakeHelper) {
        return fakeHelper.getSkipTypes().stream().anyMatch(s -> s.equals(field.getType())) ||
                fakeHelper.getSkipFields().stream().anyMatch(s -> s.equals(field.getName()));
    }
}
