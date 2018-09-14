package com.task.adesao.constants;

/**
 * Created by marcus on 14/09/18.
 */
public enum Ambiente {
    HEXT("HEXT"),
    PROD("PROD");

    private String desc;

    Ambiente(String s) {
        desc = s;
    }

    public String getDescricao() {
        return desc;
    }
}
