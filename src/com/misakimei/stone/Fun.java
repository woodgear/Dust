package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class  Fun extends ASTList {

    protected int size=-1;

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
    public void lookup(Symbols symbol) {
        size=lookup(symbol,paramters(),body());
    }


    //创建函数内部环境
    public static int lookup(Symbols symbol, ParamterList paramters, BlockStment body) {
        Symbols sym=new Symbols(symbol);//函数符号表
        paramters.lookup(sym);
        body.lookup(sym);
        return sym.size();
    }


    @Override
    public Object eval(Environment env) {
        return new Function(paramters(),body(),env, size);
    }
}
