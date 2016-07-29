package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class ParamterList extends ASTList {
    public ParamterList(List<ASTree> lis) {
        super(lis);
    }
    public String name(int i){
        return ((ASTLeaf)child(i)).token().getText();
    }
    public int size(){return  numChildren();}

    public void eval(Environment env,int index,Object val) {
        env.putNew(name(index),val);
    }
}
