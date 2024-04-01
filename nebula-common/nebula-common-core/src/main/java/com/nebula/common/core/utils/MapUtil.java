package com.nebula.common.core.utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapUtil {

    /**
     * Remove any key-value pairs from the original map that match any of the keys in the keysToExclude set.
     *
     * @param originalMap
     * @param keysToExclude
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> removeAny(Map<K, V> originalMap, Set<K> keysToExclude) {
        return originalMap.entrySet()
            .stream()
            .filter(entry -> !keysToExclude.contains(entry.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
