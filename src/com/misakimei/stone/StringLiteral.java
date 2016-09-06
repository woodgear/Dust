package com.misakimei.stone;

import com.misakimei.stone.vm.Code;
import static com.misakimei.stone.vm.Opcode.SCONST;
import static com.misakimei.stone.vm.Opcode.encodeRegister;
import static com.misakimei.stone.vm.Opcode.encodeShortOffset;

/**
 * Created by 18754 on 2016/7/27.
 */
public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }
    public String value(){
        return token().getText();
    }

    @Override
    public Object eval(Environment env) {
        return value();
    }

    @Override
    public void compiler(Code c) {
        int i=c.record(value());
        c.add(SCONST);
        c.add(encodeShortOffset(i));
        c.add(encodeRegister(c.nextReg++));
    }
}
