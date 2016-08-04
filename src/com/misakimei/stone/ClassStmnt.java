package com.misakimei.stone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ClassStmnt extends ASTList {
    public ClassStmnt(List<ASTree> lis) {
        super(lis);
    }

    public String name(){
        return ((ASTLeaf)child(0)).token().getText();
    }

    public String superClass(){
        if (numChildren()<3){
            return null;
        }
        else{
            return ((ASTLeaf)child(1)).token().getText();
        }
    }
    public ClassBody body(){
        return (ClassBody) child(numChildren()-1);
    }

    @Override
    public String toString() {
        String parent=superClass();
        if (parent==null){
            parent="*";
        }
        return "class "+name()+ " "+parent+" "+body()+" )";
    }

    @Override
    public Object eval(Environment env) {
        //一个类可以说是有一些变量和一些方法组成的
        Symbols methodNames=new MemberSymbols(env.symbol(),MemberSymbols.METHOD);
        Symbols fieldName=new MemberSymbols(methodNames,MemberSymbols.FIELD);
        OptClassInfo ci=new OptClassInfo(this,env,methodNames,fieldName);
        env.put(name(),ci);

        ArrayList<DefStmnt>methods=new ArrayList<>();
        if (ci.supperClass() !=null){
            ci.supperClass().copyTo(fieldName,methodNames,methods);
        }
        Symbols newsyms=new SymbolThis(fieldName);
        body().lookup(newsyms,methodNames,fieldName,methods);//blockstmnt 将真正填充
        ci.setMethods(methods);
        return name();
    }

    @Override
    public void lookup(Symbols symbol) {}
}
