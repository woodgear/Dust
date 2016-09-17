package com.misakimei.stone;

import com.misakimei.stone.tool.Log;
import com.misakimei.stone.type.*;
import com.misakimei.stone.vm.Code;
import com.misakimei.stone.vm.EnvEx;
import com.misakimei.stone.vm.StoneVM;
import com.misakimei.stone.vm.VMFunction;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;

import java.util.List;

import static com.misakimei.stone.type.ToJava.ENV;
import static com.misakimei.stone.type.ToJava.transloateExpr;
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
        if (!(val instanceof JavaFunction)){
            throw new StoneExcetion("无法解析成函数类型");
        }
        JavaFunction function= (JavaFunction) val;
        Object[]args=new Object[numChildren()+1];
        args[0]=env;
        int num=1;
        for (ASTree a:this){
            args[num++]=a.eval(env);
        }
        return function.invoke(args);
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

    @Override
    public String translate(String expr) {
        StringBuilder code=new StringBuilder(expr);
        code.append("(").append(ENV);
        for (int i=0;i<size();i++){
            code.append(",").append(transloateExpr(child(i),argTypes[i],funcitonType.parameterTypes[i]));
        }
        return code.append(")").toString();
    }


}
