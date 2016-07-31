package com.misakimei.stone;

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

    //def class eval 像环境中加对应得 名值
    @Override
    public Object eval(Environment env) {
        ClassInfo ci=new ClassInfo(this,env);
        env.put(name(),ci);
        return name();
    }
}
