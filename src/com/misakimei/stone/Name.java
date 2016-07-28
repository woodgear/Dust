package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class Name extends ASTLeaf {
    public Name(Token t) {
        super(t);
    }
    public String name(){return token().getText();}

    @Override
    public Object eval(Environment env) {
        Object value=env.get(name());
        if (value==null){
            throw new StoneExcetion("找不到 "+name()+" 的定义",this);
        }
        else return value;
    }
}
