package ru.astepanova.lib.fakegenerator.util;

import lombok.NonNull;
import ru.astepanova.lib.fakegenerator.FakeHelper;

public interface FakeGenerator {

    <T> T createFaked(@NonNull final Class<T> clazz);

    <T> T createFaked(@NonNull final Class<T> clazz, @NonNull final FakeHelper fakeHelper);
}
