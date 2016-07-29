package com.misakimei.stone;

import java.sql.PseudoColumnUsage;
import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class Arguments extends Postfix{


    public Arguments(List<ASTree> lis) {
        super(lis);
    }
    public int size(){return numChildren();}

    public Object eval(Environment env,Object val) {
        if (!(val instanceof Function))
        {
            throw new StoneExcetion("不是函数 ",this);
        }

        Function fun= (Function) val;
        ParamterList params=fun.getParamters();
        if (size()!=params.size()){
            throw new StoneExcetion("参数不够 ",this);
        }
        Environment nenv=fun.makeEnv();
        int num=0;
        for (ASTree a:this){
            params.eval(nenv,num++,a.eval(env));
        }
        return fun.getBody().eval(nenv);

    }
}
