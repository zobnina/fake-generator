package ru.astepanova.lib.fakegenerator.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import ru.astepanova.lib.fakegenerator.FakeHelper;

/**
 * Класс для создания экземпляра класса и заполнения его полей рандомными
 * значениями.
 * Комментарий: ожидается класс, имеющий builder от lombok (@Builder)
 *
 * @author astepanova
 */
public final class BuilderFakeGenerator extends FakeGenerator {

    @Override
    public <T> T createFaked(Class<T> clazz, FakeHelper fakeHelper) {
        try {
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
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            String msg = "Cannot instantiate and fill class " + clazz;
            LOGGER.warning(msg);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
