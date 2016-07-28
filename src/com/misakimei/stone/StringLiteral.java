package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }
    public String value(){
        return token().getText();
    }

    @Override
    public Object eval(Environment env) {
        return value();
    }
}
