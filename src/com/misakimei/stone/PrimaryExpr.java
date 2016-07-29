package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<ASTree> lis) {
        super(lis);
    }

    //擦这是何等的卧槽 这个函数的作用就是避免只有一个还被包裹的情况
    public static ASTree create(List<ASTree>lis){
        //如何只有一个 那么就直接返回这个 而不是包裹一层 如果不是则包裹
        return lis.size()==1?lis.get(0):new PrimaryExpr(lis);

    }

    public ASTree operand(){
        return child(0);
    }
    public Postfix postfix(int nest){
        return (Postfix)child(numChildren()-nest-1);
    }

    public boolean hasPostfix(int nest){
        return numChildren()-nest>1;
    }

    @Override
    public Object eval(Environment env) {
        return evalSubExpr(env,0);
    }

    private Object evalSubExpr(Environment env, int nest) {
        if (hasPostfix(nest)){
            Object target=evalSubExpr(env,nest+1);
            return postfix(nest).eval(env,target);
        }else {
            return operand().eval(env);
        }
    }

}
