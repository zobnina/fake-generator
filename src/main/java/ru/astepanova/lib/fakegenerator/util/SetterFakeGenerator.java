package ru.astepanova.lib.fakegenerator.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import lombok.NonNull;
import ru.astepanova.lib.fakegenerator.FakeHelper;

/**
 * Класс для создания экземпляра класса и заполнения его полей рандомными
 * значениями.
 * Комментарий: ожидается класс, имеющий default конструктор и setter методы
 *
 * @author astepanova
 */
public class SetterFakeGenerator extends FakeGenerator {

    @Override
    public <T> T createFaked(@NonNull Class<T> clazz) {
        return createFaked(clazz, FakeHelper.builder().build());
    }

    @Override
    public <T> T createFaked(@NonNull Class<T> clazz, @NonNull FakeHelper fakeHelper) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T instance = constructor.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                String fieldName = field.getName();
                String setterName = "set" + fieldName.toUpperCase().charAt(0)
                        + (fieldName.length() > 1 ? fieldName.substring(1) : "");
                Method setterMethod = clazz.getDeclaredMethod(setterName, field.getType());
                Object parameterValue = parameterValue(setterMethod, field, fakeHelper);
                setterMethod.invoke(instance, parameterValue);
            }
            return instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            String msg = "Cannot instantiate and fill class " + clazz;
            LOGGER.warning(msg);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
