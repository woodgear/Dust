package com.misakimei.stone;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 18754 on 2016/7/27.
 */
public class ASTLeaf extends ASTree {
    private static ArrayList<ASTree>empty=new ArrayList<>();
    private Token token;
    public ASTLeaf(Token t){
        token=t;
    }

    @Override
    public ASTree child(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public Iterator<ASTree> children() {
        return empty.iterator();
    }

    @Override
    public String location() {
        return "第"+token.getLineNumber()+"行";
    }

    @Override
    public Object eval(Environment env) {
        throw  new StoneExcetion("无法执行 eval "+toString(),this);
    }

    public Token token(){return token;}

    @Override
    public String toString() {
        return token.getText();
    }
}
