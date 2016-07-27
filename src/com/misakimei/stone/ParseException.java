package com.misakimei.stone;

import java.io.IOException;

/**
 * Created by 18754 on 2016/7/27.
 */
public class ParseException extends RuntimeException {
    public ParseException(String msg,Token t){
        super("在 "+location(t)+" 有语法错误 "+msg);
    }

    private static String location(Token t) {
        if (t==Token.EOF){
            return "最后一行";
        }
        return "第"+t.getLineNumber()+"行:"+t.getText();
    }

    public ParseException(Token t){
        this("",t);
    }

    public ParseException(IOException e){
        super(e);
    }
    public ParseException(String msg){
        super(msg);
    }
}
