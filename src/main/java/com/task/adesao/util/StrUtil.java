package com.task.adesao.util;

import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class StrUtil {
    private static final String token = "§";

    public static String formatFormulaToLine(String formula, int linha) {
        String result = null;
        result = StringUtils.replace(formula, token, Integer.toString(linha));
        return result;
    }

    public static String arrayToString(List<String> array) {
        StringBuffer sb = new StringBuffer();
        for (String str : array) {
            sb.append(str).append('\n');
        }
        return sb.toString();
    }

    public static String[] stringToArray(String script) {
        String[] aScript = script.split("\n");
        return aScript;
    }
}
