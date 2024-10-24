package ru.astepanova.lib.fakegenerator.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.astepanova.lib.fakegenerator.FakeHelper;
import ru.astepanova.lib.fakegenerator.test.SomeClass.SomeEnum;
import ru.astepanova.lib.fakegenerator.util.BuilderFakeGenerator;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class BuilderFakeGeneratorTest {

    BuilderFakeGenerator generator = new BuilderFakeGenerator();

    @Test
    @DisplayName("Без скипов и предзаполнения")
    void createFaked1Test() {
        SomeClass faked = generator.createFaked(SomeClass.class);
        assertNotNull(faked);
        assertNotNull(faked.getByteField());
        assertNotNull(faked.getShortField());
        assertNotNull(faked.getIntField());
        assertNotNull(faked.getLongField());
        assertNotNull(faked.getBigIntField());
        assertNotNull(faked.getFloatField());
        assertNotNull(faked.getDoubleField());
        assertNotNull(faked.getBigDecimalField());
        assertNotNull(faked.getBoolField());
        assertNotNull(faked.getCharField());
        assertNotNull(faked.getStrField());
        assertNotNull(faked.getUuidField());
        assertNotNull(faked.getListField());
        assertNotNull(faked.getSetField());
        assertNotNull(faked.getMapField());
        assertNotNull(faked.getByteArrField());
        assertNotNull(faked.getEnumField());
        assertNotNull(faked.getDateField());
        assertNotNull(faked.getLDateField());
        assertNotNull(faked.getLDateTimeField());
        assertNotNull(faked.getOffsetDateTimeField());
        assertNotNull(faked.getZonedDateTimeField());
    }

    @Test
    @DisplayName("Без скипов и с предзаполнением")
    void createFaked2Test() {
        byte byteField = Byte.parseByte("1");
        String str = "str";
        SomeEnum e = SomeEnum.CONST1;
        LocalDate now = LocalDate.now();
        SomeClass faked = generator.createFaked(SomeClass.class, FakeHelper.builder()
            .fieldValues(List.of(byteField, str, e, now))
            .build());
        assertEquals(byteField, faked.getByteField());
        assertEquals(str, faked.getStrField());
        assertEquals(str, faked.getStr2Field());
        assertEquals(e, faked.getEnumField());
        assertEquals(now, faked.getLDateField());
    }

    @Test
    @DisplayName("Без скипов и с предзаполнением по названию полей")
    void createFaked3Test() {
        byte byteField = Byte.parseByte("1");
        String str = "str";
        SomeEnum e = SomeEnum.CONST1;
        LocalDate now = LocalDate.now();
        SomeClass faked = generator.createFaked(SomeClass.class, FakeHelper.builder()
            .fieldValueMap(Map.of(
                "byteField", byteField,
                "strField", str,
                "enumField", e,
                "lDateField", now
            ))
            .build());
        assertEquals(byteField, faked.getByteField());
        assertEquals(str, faked.getStrField());
        assertNotEquals(str, faked.getStr2Field());
        assertEquals(e, faked.getEnumField());
        assertEquals(now, faked.getLDateField());
    }

    @Test
    @DisplayName("Со скипами типов без предзаполнения")
    void createFaked4Test() {
        SomeClass faked = generator.createFaked(SomeClass.class, FakeHelper.builder()
        .skipTypes(List.of(String.class))
        .build());

        assertNotNull(faked);
        assertNull(faked.getStrField());
        assertNull(faked.getStr2Field());
    }
    
    @Test
    @DisplayName("Со скипами по именам полей без предзаполнения")
    void createFaked5Test() {
        SomeClass faked = generator.createFaked(SomeClass.class, FakeHelper.builder()
        .skipFields(List.of("strField"))
        .build());

        assertNotNull(faked);
        assertNull(faked.getStrField());
        assertNotNull(faked.getStr2Field());
    }
}