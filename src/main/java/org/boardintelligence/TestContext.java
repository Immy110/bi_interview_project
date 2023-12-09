package org.boardintelligence;

import roman.Roman;

public class TestContext {

    private final Roman roman;

    public TestContext() {
        roman = new Roman();
    }

    public Roman getRoman() {
        return roman;
    }
}
