package com.misakimei.stone.type;

import com.misakimei.stone.StoneExcetion;
import com.misakimei.stone.tool.Log;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * Created by 18754 on 2016/9/17.
 */
public class JavaLoader {
    protected ClassLoader  loader;
    protected ClassPool cpool;

    public JavaLoader(){
        cpool=new ClassPool(null);
        cpool.appendSystemPath();
        loader=new ClassLoader(this.getClass().getClassLoader()){};
    }
    public Class<?>load(String className,String method){
        CtClass cc=cpool.makeClass(className);
        try {
            Log.d(method);
            cc.addMethod(CtMethod.make(method,cc));
            return cc.toClass(loader,null);
        } catch (CannotCompileException e) {
            e.printStackTrace();
         throw new StoneExcetion(e.getMessage());
        }
    }
}
