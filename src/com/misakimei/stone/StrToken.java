package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class StrToken extends Token {
    private String str;
    public StrToken(int lineNo, String str) {
        super(lineNo);
        this.str=str;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getText() {
        return str;
    }
}
