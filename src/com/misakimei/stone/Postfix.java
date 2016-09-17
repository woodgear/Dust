package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public abstract class Postfix extends ASTList {
    public Postfix(List<ASTree> lis) {
        super(lis);
    }

    public abstract Object eval(Environment env,Object value);
    public  TypeInfo typecheck(TypeEnv tenv,TypeInfo tatget)throws TypeException{return null;};
    public  String translate(String expr){return null;}
}
