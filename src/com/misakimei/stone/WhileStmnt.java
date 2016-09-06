package com.misakimei.stone;

import com.misakimei.stone.vm.Code;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.*;

/**
 * Created by 18754 on 2016/7/27.
 */
public class WhileStmnt extends ASTList {

    public WhileStmnt(List<ASTree> lis) {
        super(lis);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }

    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }

    @Override
    public Object eval(Environment env) {
        Object result = 0;
        while (checkCondition(env)) {
            result = body().eval(env);
        }
        return result;
    }

    private boolean checkCondition(Environment env) {
        Object conditon=condition().eval(env);
        boolean con;
        if (conditon instanceof Boolean) {
            con = ((Boolean) conditon).booleanValue();
        } else if (conditon instanceof Integer) {
            int intval = ((Integer) conditon).intValue();
            con = intval != 0 ? true : false;//非0为真
        } else {
            throw new StoneExcetion("无法判断if 语句中的条件的正确性 ", this);
        }
        return con;
    }


    @Override
    public void compiler(Code c) {
        int oldreg=c.nextReg;
        c.add(BCONST);
        c.add((byte)0);
        c.add(encodeRegister(c.nextReg++));
        int pos=c.position();
        condition().compiler(c);
        int pos2=c.position();
        c.add(IFZERO);
        c.add(encodeRegister(c.nextReg++));
        c.add(encodeShortOffset(0));
        c.nextReg=oldreg;
        body().compiler(c);
        int pos3=c.position();
        c.add(GOTO);
        c.add(encodeShortOffset(pos-pos3));
        c.set(encodeShortOffset(c.position()-pos2),pos2+2);
    }
}