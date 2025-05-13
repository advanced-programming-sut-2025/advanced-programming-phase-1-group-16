package com.group16.stardewvalley.model.Transaction;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum TransactionCommands {
    SHOW_ALL_PRODUCTS("^\\s*show\\s*all\\s*products\\s*$"),
    SHOW_AVAILABLE_PRODUCTS("^\\s*show\\s*all\\s*available\\*products\\s*$"),
    PURCHASE("^\\s*purchase\\s*(?<productName>\\S+)(?:\\s+-n\\s*(?<count>\\d+))?\\s*$"),
    SELL("^\\s*sell\\s*(?<productName>\\S+)\\s*-n\\s*(?<count>\\d+)\\s*$");



    private final String pattern;

    TransactionCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        return Pattern.compile(this.pattern).matcher(input);
    }
}
