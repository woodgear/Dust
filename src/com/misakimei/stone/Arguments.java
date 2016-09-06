package com.misakimei.stone;

import com.misakimei.stone.tool.Log;
import com.misakimei.stone.vm.Code;
import com.misakimei.stone.vm.EnvEx;
import com.misakimei.stone.vm.StoneVM;
import com.misakimei.stone.vm.VMFunction;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.*;

/**
 * Created by 18754 on 2016/7/29.
 */
public class Arguments extends Postfix{


    public Arguments(List<ASTree> lis) {
        super(lis);
    }
    public int size(){return numChildren();}

    public Object eval(Environment env,Object val) {
        //如果函数是原生函数的话 交给NativeFunction 执行
        if (val instanceof NativeFunction){
            NativeFunction fun= (NativeFunction) val;
            int numparam=fun.numParams();
            if (size()!=numparam){
                throw new StoneExcetion("函数参数不够 ",this);
            }

            Object[]arg=new Object[numparam];
            int num=0;
            for (ASTree a:this){
                arg[num++]=a.eval(env);
            }
            return fun.invoke(arg,this);
        }


        if (!(val instanceof VMFunction)){throw new StoneExcetion("无法使用此函数 ");}

        VMFunction func= (VMFunction) val;
        ParamterList params=func.getParamters();
        if (size()!=params.size()){throw new StoneExcetion("函数的参数无法匹配");}

        int num=0;
        for (ASTree t:this){
            params.eval(env,num++,t.eval(env));
        }
        StoneVM vm=((EnvEx)env).stoneVM();

        vm.run(func.entry());
        return vm.stack()[0];
    }

    @Override
    public void compiler(Code c) {
        int newoffset=c.frameSize;
        int numofargs=0;
        for (ASTree t:this){
            t.compiler(c);
            c.add(MOVE);
            c.add(encodeRegister(--c.nextReg));
            c.add(encodeOffset(newoffset++));
            numofargs++;
        }
        c.add(CALL);
        c.add(encodeRegister(--c.nextReg));
        c.add(encodeOffset(numofargs));
        c.add(MOVE);
        c.add(encodeOffset(c.frameSize));
        c.add(encodeRegister(c.nextReg++));
    }
}
