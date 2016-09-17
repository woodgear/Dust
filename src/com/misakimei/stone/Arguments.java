package com.misakimei.stone;

import com.misakimei.stone.tool.Log;
import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.type.TypeTag;
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

    protected TypeInfo[]argTypes;
    protected TypeInfo.FuncitonType funcitonType;

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


        if (!(val instanceof Function)){throw new StoneExcetion("无法使用此函数 ");}

        Function func= (Function) val;
        ParamterList params=func.getParamters();
        if (size()!=params.size()){throw new StoneExcetion("函数的参数无法匹配");}
        Environment newenv=func.makeEnv();
        int num=0;
        for (ASTree a:this){
            params.eval(newenv,num++,a.eval(env));
        }
        return func.getBody().eval(newenv);
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv, TypeInfo target) throws TypeException {
        if (!(target instanceof TypeInfo.FuncitonType)){
            throw new TypeException("函数 错误",this);
        }
        funcitonType= (TypeInfo.FuncitonType) target;
        TypeInfo[]params=funcitonType.parameterTypes;
        if (size()!=params.length){
            throw new TypeException("函数 参数数量错误",this);
        }
        argTypes=new TypeInfo[params.length];
        int num=0;
        for (ASTree a:this){
            TypeInfo t=argTypes[num]=a.typecheck(tenv);
            t.assertSubtypeOf(params[num++],tenv,this);
        }
        return funcitonType.returntype;
    }

}
