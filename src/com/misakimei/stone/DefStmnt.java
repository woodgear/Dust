package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class DefStmnt extends ASTList {

    protected int index, size;

    public DefStmnt(List<ASTree> lis) {
        super(lis);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParamterList paramters() {
        return (ParamterList) child(1);
    }

    public BlockStment body() {
        return (BlockStment) child(2);
    }

    @Override
    public void lookup(Symbols symbol) {
        index = symbol.putNew(name());//将name放入index中 下面执行时会把函数对象放在 index上
        size = Fun.lookup(symbol, paramters(), body());//size是此函数产生的变量
    }

    @Override
    public Object eval(Environment env) {
        //生成一个函数对象 被把他赋值给name()  参数 block 环境
        env.put(0, index, new Function(paramters(), body(), env, size));
        return name();
    }

    @Override
    public String toString() {
        return "(def " + name() + " " + paramters() + " " + body() + ")";
    }
}
