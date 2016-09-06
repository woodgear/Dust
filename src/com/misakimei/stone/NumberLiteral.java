package com.misakimei.stone;

import com.misakimei.stone.vm.Code;

import static com.misakimei.stone.vm.Opcode.BCONST;
import static com.misakimei.stone.vm.Opcode.ICONST;
import static com.misakimei.stone.vm.Opcode.encodeRegister;

/**
 * Created by 18754 on 2016/7/27.
 */
public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {
        return ((NumToken) token()).getValue();
    }

    @Override
    public Object eval(Environment env) {
        return value();
    }

    @Override
    public void compiler(Code c) {
        int v = value();
        if (Byte.MIN_VALUE <= v && v <= Byte.MAX_VALUE) {
            c.add(BCONST);
            c.add((byte) v);
        } else {
            c.add(ICONST);
            c.add(v);
        }
        c.add(encodeRegister(c.nextReg++));
    }
}