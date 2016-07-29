package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<ASTree> lis) {
        super(lis);
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
        Log.d(numChildren()+" child");
        Object o=child(0);
        if (o instanceof  Name){
            Log.d("name");
        }else if (o instanceof BinaryExpr){
            Log.d("binary");
        }
        Log.d("prim eval "+o.toString());
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
