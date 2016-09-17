package com.misakimei.stone;

import com.misakimei.stone.tool.Log;
import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<ASTree> lis) {
        super(lis);
    }

    //擦这是何等的卧槽 这个函数的作用就是避免只有一个还被包裹的情况
    public static ASTree create(List<ASTree> lis) {
        //如何只有一个 那么就直接返回这个 而不是包裹一层 如果不是则包裹
        return lis.size() == 1 ? lis.get(0) : new PrimaryExpr(lis);
    }

    //返回名字的部分 sum(1) 返回sum 之前def已经把 sum声明成一个对象了
    public ASTree operand() {
        return child(0);
    }

    public Postfix postfix(int nest) {
        return (Postfix) child(numChildren() - nest - 1);
    }

    //后缀 例如 sum(1) 的 (1)
    public boolean hasPostfix(int nest) {
        return numChildren() - nest > 1;
    }

    @Override
    public Object eval(Environment env) {
        return evalSubExpr(env, 0);
    }

    public Object evalSubExpr(Environment env, int nest) {

        if (hasPostfix(nest)) {
            Object target = evalSubExpr(env, nest + 1);//递归返回的是一个函数对象
            return postfix(nest).eval(env, target);//Arguments的eval 最后是这里
        } else {
            //最里层的是一个Name
            return operand().eval(env);
        }
    }

    @Override
    public void lookup(Symbols symbol) {
        super.lookup(symbol);
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        return typecheck(0, tenv);
    }

    private TypeInfo typecheck(int nest, TypeEnv tenv) {
        if (hasPostfix(nest)) {
            TypeInfo target = typecheck(nest + 1, tenv);
            return postfix(nest).typecheck(tenv, target);
        } else {
            return operand().typecheck(tenv);
        }
    }

    @Override
    public String translate(TypeInfo res) {
        return translate(0);
    }

    private String translate(int nest) {
        if (hasPostfix(nest)) {
            String expr = translate(nest + 1);
            return ((Postfix) postfix(nest)).translate(expr);
        } else {
            return operand().translate(null);
        }
    }
}
