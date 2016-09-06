package com.misakimei.stone;

import com.misakimei.stone.vm.Code;
import com.misakimei.stone.vm.Opcode;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.*;

/**
 * Created by 18754 on 2016/7/27.
 */
public class BinaryExpr extends ASTList {

    protected OptClassInfo classInfo=null;
    protected int index;


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
        } else {
            throw new StoneExcetion("无法应用 " + op, this);
        }


    }

    private Object computeNumber(Integer left, Integer right, String op) {
        int a = left.intValue();
        int b = right.intValue();
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "%":
                return a % b;
            case "==":
                return a == b;
            case ">":
                return a > b;
            case "<":
                return a < b;
            default:
                throw new StoneExcetion("无法识别的符号 " + op, this);
        }
    }

    private Object computeAssign(Environment env, Object rvalue) {
        ASTree left = left();
        //如果左式是 PrimaryExpr 类似 a.b=2  左式 a.b
        if (left instanceof PrimaryExpr) {
            PrimaryExpr p = (PrimaryExpr) left;
            if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                Object t = p.evalSubExpr(env, 1);
                if (t instanceof OptStoneObject) {
                    return setField((OptStoneObject) t, (Dot) p.postfix(0), rvalue);
                }
            } else if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                Object a=((PrimaryExpr) left).evalSubExpr(env,1);
                if (a instanceof Object[]){
                    ArrayRef ref= (ArrayRef) p.postfix(0);
                    Object index=ref.index().eval(env);

                    if (index instanceof Integer){
                        ((Object[])a)[(Integer) index]=rvalue;
                        return rvalue;
                    }
                    throw new StoneExcetion("数组访问异常");
                }
            }
        }else if (left instanceof Name) {
            ((Name) left).evalForAssign(env,rvalue);
            return rvalue;
        }
        throw new StoneExcetion("无法在此处应用 = ", this);
    }

    private Object setField(OptStoneObject obj, Dot dot, Object rvalue) {

       if (obj.classInfo()!=classInfo){
           String member=dot.name();
           classInfo=obj.classInfo();
           Integer i=classInfo.fieldIndex(member);
           if (i!=null){
               throw new StoneExcetion("无法访问 "+member,this);
           }
           index=i;
       }
        obj.write(index,rvalue);
        return rvalue;
    }


    @Override
    public void lookup(Symbols symbol) {
        ASTree left=left();
        if ("=".equals(operator())){
            if (left instanceof Name){
                ((Name) left).lookupForAssign(symbol);
                right().lookup(symbol);
                return;
            }
        }
        left.lookup(symbol);
        right().lookup(symbol);
    }

    @Override
    public void compiler(Code c)
    {
        String op=operator();
        if (op.equals("=")){
            ASTree l=left();
            if (l instanceof Name){
                right().compiler(c);
                ((Name) l).compilerAssign(c);
            }else {
                throw new StoneExcetion("错误的 =");
            }
        }else {
            left().compiler(c);
            right().compiler(c);
            c.add(getOpCode(op));
            c.add(encodeRegister(c.nextReg-2));
            c.add(encodeRegister(c.nextReg-1));
            c.nextReg--;
        }
    }
    private byte getOpCode(String op) {

        switch (op){
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "*":
                return MUL;
            case "/":
                return DIV;
            case "%":
                return REM;
            case "==":
                return EQUAL;
            case ">":
                return MORE;
            case "<":
                return LESS;
            default:throw new StoneExcetion("操作符错误 ",this);
        }
    }
}
