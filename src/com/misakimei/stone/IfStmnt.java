package com.misakimei.stone;

import com.misakimei.stone.vm.Code;
import com.misakimei.stone.vm.Opcode;
import com.sun.org.apache.bcel.internal.generic.GOTO;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.*;

/**
 * Created by 18754 on 2016/7/27.
 */
public class IfStmnt extends ASTList {

    public IfStmnt(List<ASTree> lis) {
        super(lis);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree thenBlock() {
        return child(1);
    }

    public ASTree elseBlock() {
        return numChildren() > 2 ? child(2) : null;
    }

    @Override
    public String toString() {
        return "if " + condition() + " " + thenBlock() + " else " + elseBlock() + ")";
    }

    @Override
    public Object eval(Environment env) {
        if (checkCondition(env)) {
            return thenBlock().eval(env);
        } else {
            ASTree els = elseBlock();
            if (els != null) {
                return els.eval(env);
            }
            //没有else 语句 条件为 false
            //return null;
            return 0;
        }
    }

    private boolean checkCondition(Environment env) {
        Object conditon = condition().eval(env);
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
        condition().compiler(c);
        int pos = c.position();
        c.add(IFZERO);
        c.add(encodeRegister(--c.nextReg));
        c.add(encodeShortOffset(0));
        int oldreg = c.nextReg;
        thenBlock().compiler(c);
        int pos2 = c.position();
        c.add(Opcode.GOTO);
        c.add(encodeShortOffset(0));
        c.set(encodeShortOffset(c.position() - pos), pos + 2);//修改 设置正确的ifzero的跳转值
        ASTree b = elseBlock();
        c.nextReg = oldreg;
        if (b != null) {
            b.compiler(c);
        } else {
            c.add(BCONST);
            c.add((byte) 0);
            c.add(encodeRegister(c.nextReg++));
        }
        c.set(encodeShortOffset(c.position()-pos2),pos2+1);
    }
}