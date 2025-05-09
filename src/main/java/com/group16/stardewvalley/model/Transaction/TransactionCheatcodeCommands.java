package com.group16.stardewvalley.model.Transaction;

import java.util.regex.*;

public enum TransactionCheatcodeCommands {

    ADD("^\\s*cheat\\s*add\\s*(?<count>\\s+)\\s*dollars\\s*$");



    private final String pattern;

    TransactionCheatcodeCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        return Pattern.compile(this.pattern).matcher(input);
    }


}
