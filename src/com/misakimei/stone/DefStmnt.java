package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class DefStmnt extends ASTList {

    public DefStmnt(List<ASTree> lis) {
        super(lis);
    }
    public String name(){
        return ((ASTLeaf)child(0)).token().getText();
    }
    public ParamterList paramters(){
        return (ParamterList) child(1);
    }
    public BlockStment body(){
        return (BlockStment) child(2);
    }

    @Override
    public Object eval(Environment env) {
        //先获取参数生成函数内部作用域 在给body设置作用域并执行
       env.putNew(name(),new Function(paramters(),body(),env));
        return name();
    }

    @Override
    public String toString() {
        return "(def "+name()+" "+paramters()+" "+body()+")";
    }
}
