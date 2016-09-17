package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;
import com.misakimei.stone.vm.Opcode;
import com.sun.org.apache.bcel.internal.generic.GOTO;

import java.util.List;

import static com.misakimei.stone.type.ToJava.returnZero;
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
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        TypeInfo condtype = condition().typecheck(tenv);
        condtype.assertSubtypeOf(TypeInfo.INT, tenv, this);
        TypeInfo thenType = thenBlock().typecheck(tenv);
        TypeInfo elseType;
        ASTree elsebl = elseBlock();
        if (elsebl == null) {
            elseType = TypeInfo.INT;
        } else {
            elseType = elsebl.typecheck(tenv);
        }
        return thenType.union(elseType, tenv);
    }

    @Override
    public String translate(TypeInfo res) {
        StringBuilder code = new StringBuilder();
        code.append("if(");
        code.append(condition().translate(null));
        code.append("!=0){\n");
        code.append(thenBlock().translate(res));
        code.append("}else{\n");
        ASTree elsebl = elseBlock();
        if (elsebl != null) {
            code.append(elsebl.translate(res));
        } else if (res != null) {
            code.append(returnZero(res));
        }
        return code.append("}\n").toString();

    }
}