package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class IdToken extends Token {
    private String text;
    public IdToken(int lineNo, String eol) {
        super(lineNo);
        text=eol;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    @Override
    public String getText() {
        return text;
    }
}
