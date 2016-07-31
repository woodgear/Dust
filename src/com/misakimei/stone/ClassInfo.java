package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ClassInfo {

    private ClassStmnt define;
    private Environment environment;
    private ClassInfo   superclass;

    public ClassInfo(ClassStmnt classStmnt, Environment env) {
        this.define=classStmnt;
        this.environment=env;

        Object ss=env.get(define.superClass());
        if (ss instanceof ClassInfo){
            superclass= (ClassInfo) ss;
        }else if (ss==null){
            superclass=null;
        }else {
            throw new StoneExcetion("无法确认父类 "+define.superClass());
        }
    }

    public String name(){
        return define.name();
    }

    public Environment enviroment() {
        return environment;
    }

    public ClassInfo supperClass() {
        return superclass;
    }

    public ClassBody body() {
        return define.body();
    }

    @Override
    public String toString() {
        return "< class "+name()+" >";
    }
}
