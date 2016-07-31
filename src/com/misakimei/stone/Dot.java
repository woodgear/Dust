package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/31.
 */
public class Dot extends Postfix {
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
        if (value instanceof ClassInfo) {
            if ("new".equals(member))//使用new 来构造
            {
                ClassInfo ci = (ClassInfo) value;
                NestEnv nestEnv = new NestEnv(ci.enviroment());
                StoneObject so = new StoneObject(nestEnv);
                nestEnv.put("this", so);//类的环境中 this 指向代表这个类的StoneObject
                initObject(ci, nestEnv);
                return so;
            }
        } else if (value instanceof StoneObject)//普通的变量读取 或者类的方法调用
        {
            try {
                return ((StoneObject) value).read(member);
            } catch (StoneObject.AccessException e) {
                throw new StoneExcetion("访问异常 无法读取 " + member);
            }
        }
        return null;
    }


    //类的初始化 先初始化父类
    private void initObject(ClassInfo ci, Environment env) {
        if (ci.supperClass() != null) {
            initObject(ci.supperClass(), env);
        }
        ci.body().eval(env);

    }

}
