package com.misakimei.stone.type;

import com.misakimei.stone.*;

import java.util.Iterator;
import java.util.List;

import static com.misakimei.stone.type.ToJava.LOCAL;
import static com.misakimei.stone.type.ToJava.transloateExpr;

/**
 * Created by 18754 on 2016/9/12.
 */
public class VarStmnt extends ASTList {
    protected int index;
    protected TypeInfo vartype,valuetype;


    public VarStmnt(List<ASTree> lis) {
        super(lis);
    }
    public String name(){
        return ((ASTLeaf)child(0)).token().getText();
    }
    public TypeTag type(){
        return (TypeTag) child(1);
    }
    public ASTree initializer(){
        return child(2);
    }

    public void lookup(Symbols syms){
        index=syms.putNew(name());
        initializer().lookup(syms);
    }

    @Override
    public Object eval(Environment env) {
        Object val=initializer().eval(env);
        env.put(0,index,val);
        return val;
    }

    @Override
    public String toString() {
        return "(var "+name()+" "+type()+" "+initializer()+")";
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        if (tenv.get(0,index)!=null)
        {
            throw new TypeException("定义重复 "+name(),this);
        }
        vartype=TypeInfo.get(type());
        tenv.put(0,index,vartype);
        valuetype=initializer().typecheck(tenv);
        valuetype.assertSubtypeOf(vartype,tenv,this);
        return vartype;
    }

    @Override
    public String translate(TypeInfo res) {
        return LOCAL+index+"="+transloateExpr(initializer(),valuetype,vartype);
    }
}