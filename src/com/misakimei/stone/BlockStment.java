package com.misakimei.stone;

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
    public void compiler(Code c) {
        if (this.numChildren()>0){
            int initreg=c.nextReg;
            for (ASTree a:this){
                c.nextReg=initreg;
                a.compiler(c);
            }
        }else {
            c.add(BCONST);
            c.add((byte) 0);
            c.add(encodeRegister(c.nextReg++));
        }
    }
}
