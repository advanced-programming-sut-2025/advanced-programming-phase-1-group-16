package com.group16.stardewvalley.model.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GadgetsCommands {
   EQUIP("^\\s*tools\\s*equip (?<toolName>.+?)\\s*$"),
   AVAILABLE_TOOLS("^\\s*tools\\s*show\\s*available\\s*$"),
   UPGRADE_TOOLS("^\\s*tools\\s*upgrade\\s*(?<toolName>.+?>)\\s*$"),
   USE_TOOL("^\\s*tools\\s*use\\s*-d\\s*(?<direction>.+?)\\s*$");

   private final String pattern;

   GadgetsCommands(String pattern) {
       this.pattern = pattern;
   }

   public Matcher getMatcher(String input) {
       return Pattern.compile(this.pattern).matcher(input);
   }



}