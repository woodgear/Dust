package com.misakimei.stone;

import com.misakimei.stone.tool.Log;
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

    public DefStmnt(List<ASTree> lis) {
        super(lis);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParamterList paramters() {
        return (ParamterList) child(1);
    }

    public BlockStment body() {
        return (BlockStment) child(2);
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
//        env.put(0, index, new OptFunction(paramters(), body(), env, size));
//        return name();
        String funcname=name();
        EnvEx vmenv= (EnvEx) env;
        Code code=vmenv.code();
        int entry=code.position();
        compiler(code);
        env.putNew(funcname,new VMFunction(paramters(),body(),env,entry));

        return funcname;
    }

    @Override
    public String toString() {
        return "(def " + name() + " " + paramters() + " " + body() + ")";
    }

    public void lookupAsMethod(Symbols sym) {
        Symbols newsyms = new Symbols(sym);
        newsyms.putNew(SymbolThis.name);
        paramters().lookup(newsyms);
        body().lookup(newsyms);
        size = newsyms.size();
        }

    @Override
    public void compiler(Code c) {
        c.nextReg=0;
        c.frameSize=size+ StoneVM.SAVE_AREA_SIZE;

        c.add(SAVE);
        c.add(encodeOffset(size));

        body().compiler(c);//在这里 生成function的代码

        c.add(MOVE);
        c.add(encodeRegister(c.nextReg-1));
        c.add(encodeOffset(0));
        c.add(RESTORE);
        c.add(encodeOffset(size));
        c.add(RETURN);
    }
}
