package com.task.adesao.util;

import org.springframework.util.StringUtils;

/**
 * Created by marcus on 14/09/18.
 */
public class StrUtil {
    private static final String token = "§";
    public static String formatFormulaToLine (String formula, int linha) {
        String result = null;
        result = StringUtils.replace(formula, token, Integer.toString(linha));
        return result;
    }
}
