package com.misakimei.stone;

import java.util.List;

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
}
