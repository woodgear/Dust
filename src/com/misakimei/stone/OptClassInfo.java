package com.misakimei.stone;

import java.util.ArrayList;

/**
 * Created by 18754 on 2016/8/2.
 */
public class OptClassInfo extends ClassInfo {
    protected Symbols methods,field;
    protected DefStmnt[]methodDefs;
    public OptClassInfo(ClassStmnt classStmnt, Environment env,Symbols method,Symbols field) {
        super(classStmnt, env);
        this.methods=method;
        this.field=field;
        this.methodDefs=null;
    }
    public int size(){
        return field.size();
    }

    @Override
    public OptClassInfo supperClass() {
        return (OptClassInfo) super.supperClass();
    }
    //将父类的 field method 拷贝到子类中去
    public void copyTo(Symbols f, Symbols m, ArrayList<DefStmnt>mlist){
        f.append(field);
        m.append(methods);
        for (DefStmnt def:methodDefs){
            mlist.add(def);
        }
    }

    public Integer fieldIndex(String name){
        return  field.find(name);
    }


    public Integer methodIndex(String name){
        return methods.find(name);
    }
    public Object method(OptStoneObject self,int index){
        DefStmnt def=methodDefs[index];
        return new OptMethod(def.paramters(),def.body(),enviroment(),def.locals(),self);
    }

    public void setMethods(ArrayList<DefStmnt> methods){
        methodDefs=methods.toArray(new DefStmnt[methods.size()]);
    }


}
