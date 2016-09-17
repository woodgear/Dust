package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.List;

import static com.misakimei.stone.type.ToJava.returnZero;
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
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        TypeInfo condtype=condition().typecheck(tenv);
        condtype.assertSubtypeOf(TypeInfo.INT,tenv,this);
        TypeInfo bodytype=body().typecheck(tenv);
        return bodytype.union(TypeInfo.INT,tenv);
    }

    @Override
    public String translate(TypeInfo res) {
        String code="while("+condition().translate(null)+"!=0){\n"
                +body().translate(res)
                +"}\n";
        if (res==null){
            return code;
        }else{
            return returnZero(res)+"\n"+code;
        }
    }
}