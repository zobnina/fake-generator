package ru.astepanova.lib.fakegenerator.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SomeClass {

    Byte byteField;

    Short shortField;

    Integer intField;

    Long longField;

    BigInteger bigIntField;

    Float floatField;

    Double doubleField;

    BigDecimal bigDecimalField;

    Boolean boolField;

    Character charField;

    String strField;

    String str2Field;

    UUID uuidField;

    List<Integer> listField;

    Set<String> setField;

    Map<String, Integer> mapField;

    byte[] byteArrField;

    SomeEnum enumField;

    Date dateField;

    LocalDate lDateField;

    LocalDateTime lDateTimeField;

    OffsetDateTime offsetDateTimeField;

    ZonedDateTime zonedDateTimeField;

    enum SomeEnum {
        CONST1,
        CONST2
    }
}
