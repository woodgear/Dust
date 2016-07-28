package com.misakimei.stone;

import java.util.Iterator;

/**
 * Created by 18754 on 2016/7/27.
 */
public abstract class ASTree implements Iterable<ASTree>{
    public abstract ASTree child(int i);
    public abstract int numChildren();
    public abstract Iterator<ASTree> children();
    public abstract String location();
    public abstract Object eval(Environment env);
    public Iterator<ASTree> iterator(){
        return children();
    }

}
