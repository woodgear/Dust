package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }
    public int value(){return ((NumToken)token()).getValue();}
}
