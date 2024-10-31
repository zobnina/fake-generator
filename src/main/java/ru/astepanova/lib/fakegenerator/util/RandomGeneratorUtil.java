package ru.astepanova.lib.fakegenerator.util;

import com.github.javafaker.Faker;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@UtilityClass
public class RandomGeneratorUtil {

    private static final Logger LOGGER = Logger.getLogger(RandomGeneratorUtil.class.getName());
    @Getter
    private static final Faker FAKER = new Faker();

    public <V> V randomValue(Class<V> type) {

        if (nonNull(type)) {
            //Check integer types
            if (isByte(type)) {
                return (V) randomByte();
            }
            if (isShort(type)) {
                return (V) randomShort();
            }
            if (isInt(type)) {
                return (V) Integer.valueOf(RandomUtils.nextInt());
            }
            if (isLong(type)) {
                return (V) Long.valueOf(RandomUtils.nextLong());
            }
            if (BigInteger.class.isAssignableFrom(type)) {
                return (V) BigInteger.valueOf(RandomUtils.nextLong());
            }

            //Check float types
            if (isFloat(type)) {
                return (V) Float.valueOf(RandomUtils.nextFloat());
            }
            if (isDouble(type)) {
                return (V) Double.valueOf(RandomUtils.nextDouble());
            }
            if (BigDecimal.class.isAssignableFrom(type)) {
                return (V) BigDecimal.valueOf(RandomUtils.nextDouble());
            }

            //Check bool type
            if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
                return (V) Boolean.valueOf(RandomUtils.nextBoolean());
            }

            //Check symbol types
            if (isChar(type)) {
                return (V) Character.valueOf(FAKER.lorem().character());
            }
            if (String.class.isAssignableFrom(type)) {
                return (V) FAKER.lorem().characters();
            }

            //Check uuid
            if (UUID.class.isAssignableFrom(type)) {
                return (V) UUID.randomUUID();
            }

            //Check collections
            if (type.isAssignableFrom(List.class)) {
                return (V) Collections.emptyList();
            }
            if (type.isAssignableFrom(Set.class)) {
                return (V) Collections.emptySet();
            }
            if (type.isAssignableFrom(Map.class)) {
                return (V) Collections.emptyMap();
            }

            if (byte[].class.isAssignableFrom(type)) {
                return (V) RandomUtils.nextBytes(RandomUtils.nextInt());
            }

            //Check enum
            if (type.isEnum()) {
                return randomEnum(type);
            }

            //Check dates
            if (type.isAssignableFrom(Date.class)) {
                return (V) FAKER.date().birthday();
            }
            if (LocalDate.class.isAssignableFrom(type)) {
                return (V) randomLocalDate();
            }
            if (LocalDateTime.class.isAssignableFrom(type)) {
                return (V) randomLocalDateTime();
            }
            if (OffsetDateTime.class.isAssignableFrom(type)) {
                return (V) randomOffsetDateTime();
            }
            if (ZonedDateTime.class.isAssignableFrom(type)) {
                return (V) randomZonedDateTime();
            }
        }

        LOGGER.warning("Unsupported type to generate random value: " + type);
        return null;
    }

    private static <V> V randomEnum(Class<V> type) {
        V[] values = type.getEnumConstants();
        return values[FAKER.number().numberBetween(0, values.length)];
    }

    private static ZonedDateTime randomZonedDateTime() {
        return FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault());
    }

    private static OffsetDateTime randomOffsetDateTime() {
        return FAKER.date().birthday().toInstant().atOffset(ZoneOffset.UTC);
    }

    private static LocalDateTime randomLocalDateTime() {
        return randomZonedDateTime().toLocalDateTime();
    }

    private static LocalDate randomLocalDate() {
        return randomZonedDateTime().toLocalDate();
    }

    private static <V> boolean isChar(Class<V> type) {
        return Character.class.isAssignableFrom(type) || char.class.isAssignableFrom(type);
    }

    private static <V> boolean isDouble(Class<V> type) {
        return Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type);
    }

    private static <V> boolean isFloat(Class<V> type) {
        return Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type);
    }

    private static <V> boolean isLong(Class<V> type) {
        return Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type);
    }

    private static <V> boolean isInt(Class<V> type) {
        return Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type);
    }

    private static Short randomShort() {
        return (short) FAKER.number().numberBetween(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    private static <V> boolean isShort(Class<V> type) {
        return Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type);
    }

    private static Byte randomByte() {
        return (byte) FAKER.number().numberBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    private static <V> boolean isByte(Class<V> type) {
        return Byte.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type);
    }
}
