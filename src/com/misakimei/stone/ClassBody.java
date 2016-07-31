package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ClassBody extends ASTList {
    public ClassBody(List<ASTree> lis) {
        super(lis);
    }

    @Override
    public Object eval(Environment env) {
         for (ASTree t:this){
            t.eval(env);
        }
        return null;
    }
}
