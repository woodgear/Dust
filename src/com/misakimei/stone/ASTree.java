package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;

import java.util.Iterator;

/**
 * Created by 18754 on 2016/7/27.
 */
public abstract class ASTree implements Iterable<ASTree>{
    public abstract ASTree child(int i);
    public abstract int numChildren();
    public abstract Iterator<ASTree> children();
    public abstract String location();
    public abstract Object eval(Environment env);
    public Iterator<ASTree> iterator(){
        return children();
    }
    public void lookup(Symbols symbol){}

    public TypeInfo typecheck(TypeEnv tenv)throws TypeException {
        return null;
    }
    public String translate(TypeInfo res){return "";}

}
