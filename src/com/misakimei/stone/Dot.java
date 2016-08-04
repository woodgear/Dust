package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/31.
 */
public class Dot extends Postfix {

    protected OptClassInfo classInfo=null;
    protected boolean isField;
    protected int index;


    public Dot(List<ASTree> lis) {
        super(lis);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    @Override
    public String toString() {
        return "." + name();
    }


    @Override
    public Object eval(Environment env, Object value) {
        String member = name();
        if (value instanceof OptClassInfo) {
            if ("new".equals(member))//使用new 来构造
            {
                OptClassInfo ci = (OptClassInfo) value;
                ArrayEnv nestEnv = new ArrayEnv(1,ci.enviroment());
                OptStoneObject so = new OptStoneObject(ci,ci.size());
                nestEnv.put(0,0, so);//类的环境中 this 指向代表这个类的StoneObject
                initObject(ci,so, nestEnv);
                return so;
            }
        } else if (value instanceof OptStoneObject)//普通的变量读取 或者类的方法调用
        {
            OptStoneObject target= (OptStoneObject) value;
            if (target.classInfo()!=classInfo){
                updateCache(target);
            }
            if (isField){
                return target.read(index);
            }else {
                return target.method(index);
            }
        }
        return null;
    }

    private void updateCache(OptStoneObject target) {
        String member=name();
        classInfo=target.classInfo();
        Integer i=classInfo.fieldIndex(member);
        if (i!=null){
            isField=true;
            index=i;
            return;
        }

        i=classInfo.methodIndex(member);
        if (i!=null){
            isField=false;
            index=i;
            return;
        }

        throw new StoneExcetion("无法访问 "+member,this);

    }


    //类的初始化 先初始化父类
    private void initObject(OptClassInfo ci,OptStoneObject obj, Environment env) {
        if (ci.supperClass() != null) {
            initObject(ci.supperClass(),obj, env);
        }
        ci.body().eval(env);
    }
}
