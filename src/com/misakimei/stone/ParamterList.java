package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class ParamterList extends ASTList {

    protected int[]offsets;//name的index
    public ParamterList(List<ASTree> lis) {
        super(lis);
    }
    public String name(int i){
        return ((ASTLeaf)child(i)).token().getText();
    }
    public int size(){return  numChildren();}

    @Override
    public void lookup(Symbols symbol) {
        int s=size();
        offsets=new int[s];
        for(int i=0;i<s;i++){
            offsets[i]=symbol.putNew(name(i));
        }
    }

    public void eval(Environment env, int index, Object val) {
        env.put(0,offsets[index],val);
    }
}
