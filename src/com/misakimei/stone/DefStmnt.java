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
import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;

import java.util.List;

import static com.misakimei.stone.vm.Opcode.*;

/**
 * Created by 18754 on 2016/7/29.
 */
public class DefStmnt extends ASTList {

    protected int index, size;

    protected TypeInfo.FuncitonType functype;
    protected TypeEnv bodyenv;


    public DefStmnt(List<ASTree> lis) {
        super(lis);
    }


    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParamterList paramters() {
        return (ParamterList) child(1);
    }

    public TypeTag type() {
        return (TypeTag) child(2);
    }

    public BlockStment body() {
        return (BlockStment) child(3);
    }

    @Override
    public void lookup(Symbols symbol) {
        index = symbol.putNew(name());//将name放入index中 下面执行时会把函数对象放在 index上
        size = Fun.lookup(symbol, paramters(), body());//size是此函数产生的变量
    }

    public int locals() {
        return size;
    }

    @Override
    public Object eval(Environment env) {
        //生成一个函数对象 被把他赋值给name()  参数 block 环境
        env.put(0, index, new OptFunction(paramters(), body(), env, size));
        return name();
    }

    @Override
    public String toString() {
        return "(def " + name() + " " + paramters() + " " + type() + " " + body() + ")";
    }

    public void lookupAsMethod(Symbols sym) {
        Symbols newsyms = new Symbols(sym);
        newsyms.putNew(SymbolThis.name);
        paramters().lookup(newsyms);
        body().lookup(newsyms);
        size = newsyms.size();
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        TypeInfo[]params=paramters().types();
        TypeInfo retType=TypeInfo.get(type());
        functype=TypeInfo.function(retType,params);
        TypeInfo oldType=tenv.put(0,index,functype);
        if (oldType!=null){
            throw new TypeException("函数被重新定义 "+name(),this);
        }
        bodyenv=new TypeEnv(size,tenv);
        for (int i=0;i<params.length;i++){
            bodyenv.put(0,i,params[i]);
        }
        TypeInfo bodyType=body().typecheck(bodyenv);
        bodyType.assertSubtypeOf(retType,tenv,this);

        TypeInfo.FuncitonType func=functype.toFuncitonType();
        for (TypeInfo t:func.parameterTypes){
            fixUnknown(t);
        }
        fixUnknown(func.returntype);
        return func;

    }

    private void fixUnknown(TypeInfo t) {
        if (t.isUnknowType()){
            TypeInfo.UnknownType ut=t.toUnknowType();
            if (!ut.resloved()){
                ut.setType(TypeInfo.ANY);
            }
        }
    }
}
