package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class Name extends ASTLeaf {
    public Name(Token t) {
        super(t);
    }
    public String name(){return token().getText();}
}
