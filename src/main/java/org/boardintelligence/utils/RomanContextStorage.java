package org.boardintelligence.utils;

import org.boardintelligence.enums.Context;
import roman.Roman;
import java.util.*;

public class RomanContextStorage {
    private final Roman roman;

    public RomanContextStorage(Roman roman) {
        this.roman = roman;
    }

    public String getContextValueOrThrowException(Context key) {
        var keyString = key.toString();
        var contextValue = roman.getContext(keyString);
        return String.valueOf(
                Optional.ofNullable(contextValue)
                        .orElseThrow(() -> new RuntimeException("Cannot find %s in context".formatted(keyString)))
        );
    }

    public List<String> getContextValues(Context key) {
        var keyString = key.toString();
        var contextValues = roman.getContext(keyString);
        return new ArrayList<>((Collection<String>) contextValues);
    }

    public void setContextValue(
            Context key,
            String value
    ) {
        roman.setContext(key.toString(), value);
    }

    public void setContextValues(
            Context key,
            List<String> values
    ) {
        roman.setContext(key.toString(), values);
    }
}
