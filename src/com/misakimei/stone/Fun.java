package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class  Fun extends ASTList {

    public Fun(List<ASTree> lis) {
        super(lis);
    }
    public ParamterList paramters(){
        return (ParamterList) child(0);
    }
    public BlockStment body(){
        return (BlockStment) child(1);
    }

    @Override
    public String toString() {
        return "(fun "+paramters()+body()+")";
    }

    @Override
    public Object eval(Environment env) {
        return new Function(paramters(),body(),env);
    }
}
