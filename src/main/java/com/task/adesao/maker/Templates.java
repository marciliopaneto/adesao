package com.task.adesao.maker;

import com.task.adesao.constants.Ambiente;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class Templates {
    private static final String[] header = {"CONNECT TO NPCX;", "SET SCHEMA NPC;", ""};
    private static final String trailer = "connect reset;";

    public static List getHeader(Ambiente ambiente) {
        String[] result = header.clone();
        String a = ambiente.getDescricao().substring(0, 1);
        result[0] = StringUtils.replace(result[0], "X", a);
        List list = new ArrayList<>(result.length);
        for (String s : result)
            list.add(s);
        return list;
    }

    public static String getTrailer() {
        return trailer;
    }
}
