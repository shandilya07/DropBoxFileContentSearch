package com.shandilya.hevo.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String tokenize(String input) {
        Set<String> words = new HashSet<>();
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            words.add(matcher.group().toLowerCase());
        }
        return String.join(",", words);
    }
}