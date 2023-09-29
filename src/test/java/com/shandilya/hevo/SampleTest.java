package com.shandilya.hevo;

import com.shandilya.hevo.util.StringUtil;
import org.junit.jupiter.api.Test;

public class SampleTest {

    @Test
    public void testTokenization() {
        String tokenize = StringUtil.tokenize("There is a special requirement. For a string like ramendu@gmail.com, the tokenization ");
        System.out.println(tokenize);
    }
}
