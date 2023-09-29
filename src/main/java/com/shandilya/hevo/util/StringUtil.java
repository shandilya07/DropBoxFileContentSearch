package com.shandilya.hevo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String tokenize(String input) {
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            words.add(matcher.group());
        }

        return String.join(",", words);
    }
}