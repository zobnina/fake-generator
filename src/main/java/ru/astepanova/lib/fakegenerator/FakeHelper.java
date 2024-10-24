package ru.astepanova.lib.fakegenerator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FakeHelper {

    @Builder.Default
    List<Object> fieldValues = new ArrayList<>();

    @Builder.Default
    Map<String, Object> fieldValueMap = new HashMap<>();

    @Builder.Default
    List<Class<?>> skipTypes = new ArrayList<>();

    @Builder.Default
    List<String> skipFields = new ArrayList<>();
}
