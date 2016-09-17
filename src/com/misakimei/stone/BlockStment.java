package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.BCONST;
import static com.misakimei.stone.vm.Opcode.encodeRegister;

/**
 * Created by 18754 on 2016/7/27.
 */
public class BlockStment extends ASTList {
    public BlockStment(List<ASTree> lis) {
        super(lis);
    }

    TypeInfo type;

    @Override
    public Object eval(Environment env) {
        Object result=0;
        for (ASTree ast:this){
            if (!(ast instanceof NULLStmnt)){
                result=ast.eval(env);
            }
        }
        return result;
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        type=TypeInfo.INT;
        for (ASTree a:this){
            if (!(a instanceof NULLStmnt)){
                type=a.typecheck(tenv);
            }
        }
        return type;
    }
}
