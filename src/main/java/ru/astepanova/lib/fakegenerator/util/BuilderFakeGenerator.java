package ru.astepanova.lib.fakegenerator.util;

import ru.astepanova.lib.fakegenerator.FakeHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/**
 * Класс для создания экземпляра класса и заполнения его полей рандомными значениями.
 * Комментарий: ожидается класс, имеющий builder от lombok (@Builder)
 *
 * @author astepanova
 */
public final class BuilderFakeGenerator implements FakeGenerator {

    private static final Logger LOGGER = Logger.getLogger(BuilderFakeGenerator.class.getName());

    @Override
    public <T> T createFaked(Class<T> clazz) {
        return createFaked(clazz, FakeHelper.builder().build());
    }

    @Override
    public <T> T createFaked(Class<T> clazz, FakeHelper fakeHelper) {
        try {
            if (clazz != null) {
                Method builderMethod = clazz.getDeclaredMethod("builder");
                Object builderObject = builderMethod.invoke(null);
                assert builderObject != null;

                Class<?> builderClass = builderObject.getClass();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        Method fieldMethod = builderClass.getDeclaredMethod(field.getName(), field.getType());
                        Object parameterValue = parameterValue(fieldMethod, field, fakeHelper);
                        fieldMethod.invoke(builderObject, parameterValue);
                    }
                }
                Method buildMethod = builderClass.getDeclaredMethod("build");
                return (T) buildMethod.invoke(builderObject);
            }
            throw new IllegalArgumentException("Cannot instantiate and fill class because type is null");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            String msg = "Cannot instantiate and fill class " + clazz;
            LOGGER.warning(msg);
            throw new IllegalArgumentException(msg, e);
        }
    }

    private Object parameterValue(Method fieldMethod, Field field, FakeHelper fakeHelper) {
        Class<?>[] parameterTypes = fieldMethod.getParameterTypes();
        assert parameterTypes.length == 1;

        Class<?> parameterClass = parameterTypes[0];
        Object parameterValue = null;
        if (!skip(field, fakeHelper)) {
            if (fakeHelper.getFieldValueMap().containsKey(field.getName())) {
                parameterValue = fakeHelper.getFieldValueMap().get(field.getName());
            } else {
                parameterValue = fakeHelper.getFieldValues().stream()
                    .filter(v -> v.getClass().equals(parameterClass))
                    .findFirst()
                    .orElseGet(() -> RandomGeneratorUtil.randomValue(parameterClass));
            }
        }

        return parameterValue;
    }

    private static boolean skip(Field field, FakeHelper fakeHelper) {
        return fakeHelper.getSkipTypes().stream().anyMatch(s -> s.equals(field.getType())) ||
            fakeHelper.getSkipFields().stream().anyMatch(s -> s.equals(field.getName()));
    }
}
