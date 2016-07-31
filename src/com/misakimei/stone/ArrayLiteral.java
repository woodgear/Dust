package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ArrayLiteral extends ASTList {
    public ArrayLiteral(List<ASTree> lis) {
        super(lis);
    }

    public int size(){
        return numChildren();
    }

    @Override
    public Object eval(Environment env) {
        int size=numChildren();
        Object[]res=new Object[size];
        int i=0;
        for (ASTree ast:this){
            res[i++]=ast.eval(env);
        }
        return res;
    }
}
