package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.NEG;
import static com.misakimei.stone.vm.Opcode.encodeRegister;

/**
 * Created by 18754 on 2016/7/27.
 */
public class NegativeExpr extends ASTList {

    public NegativeExpr(List<ASTree> lis) {
        super(lis);
    }
    public  ASTree operand(){
        return child(0);
    }

    @Override
    public String toString() {
        return "-"+operand();
    }

    @Override
    public Object eval(Environment env) {
        Object v=operand().eval(env);
        if (v instanceof Integer){
            return new Integer(-((Integer) v).intValue());
        }
        else
            throw new StoneExcetion("无法对此类型使用-号",this);
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        TypeInfo t=operand().typecheck(tenv);
        t.assertSubtypeOf(TypeInfo.INT,tenv,this);
        return TypeInfo.INT;
    }

    @Override
    public String translate(TypeInfo res) {
        return "-"+operand().translate(null);
    }
}
