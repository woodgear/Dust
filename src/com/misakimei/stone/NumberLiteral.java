package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
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
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        return TypeInfo.INT;
    }

    @Override
    public String translate(TypeInfo res) {
        return Integer.toString(value());
    }
}