package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class BinaryExpr extends ASTList {

    public BinaryExpr(List<ASTree> lis) {
        super(lis);
    }

    public ASTree left() {
        return child(0);
    }

    public ASTree right() {
        return child(2);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token().getText();
    }

    //有两个操作数的表达式
    @Override
    public Object eval(Environment env) {
        String op = operator();
        if (op.equals("=")) {
            //=
            Object right = right().eval(env);

            return computeAssign(env, right);
        } else {
            Object left = left().eval(env);
            Object right = right().eval(env);
            return computeOP(left, right, op);

        }
    }

    //如果两边是数字 直接计算
    private Object computeOP(Object left, Object right, String op) {
        if (left instanceof Integer && right instanceof Integer) {
            return computeNumber((Integer) left, (Integer) right, op);
        } else if (op.equals("+")) {
            return String.valueOf(left) + String.valueOf(right);
        } else if (op.equals("==")) {
            if (left == null) {
                return right == null ? true : false;
            } else {
                return left.equals(right);
            }
        }else {
            throw new StoneExcetion("无法应用 "+op,this);
        }


    }

    private Object computeNumber(Integer left, Integer right, String op) {
        int a=left.intValue();
        int b=right.intValue();
        switch (op){
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "/":
                return a/b;
            case "%":
                return a%b;
            case "==":
                return a==b;
            case ">":
                return a>b;
            case "<":
                return a<b;
            default:
                throw new StoneExcetion("无法识别的符号 "+op,this);
        }
    }

    private Object computeAssign(Environment env, Object rvalue) {
        ASTree left = left();
        //如果左式是 PrimaryExpr 类似 a.b=2  左式 a.b
        if (left instanceof PrimaryExpr){
            PrimaryExpr p= (PrimaryExpr) left;
            if (p.hasPostfix(0)&&p.postfix(0) instanceof Dot){
                Object t= p.evalSubExpr(env,1);
                if (t instanceof StoneObject){
                    return setField((StoneObject)t,(Dot)p.postfix(0),rvalue);
                }
            }
        }

        if (left instanceof Name) {
            env.put(((Name) left).name(), rvalue);

            return rvalue;
        }
        throw new StoneExcetion("无法在此处应用 = ", this);
    }

    private Object setField(StoneObject so, Dot dot, Object rvalue) {
        String name=dot.name();
        try {
            so.write(name,rvalue);
        } catch (StoneObject.AccessException e) {
            throw new StoneExcetion("访问异常 无法写入 "+name+" "+rvalue);
        }
        return rvalue;
    }
}
