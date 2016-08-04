package com.misakimei.stone;

/**
 * Created by 18754 on 2016/7/27.
 */
public class Name extends ASTLeaf {

    protected static final int UNKNOWN=-1;
    protected int nest,index;
    public Name(Token t) {
        super(t);index=UNKNOWN;
    }

    public String name(){return token().getText();}

    @Override
    public void lookup(Symbols symbol) {
        Symbols.Location loc=symbol.get(name());

        if (loc==null){
            throw new StoneExcetion("未定义的名称 "+name(),this);
        }else {
            nest=loc.nest;
            index=loc.index;
        }
    }

    public void lookupForAssign(Symbols sym){
        Symbols.Location loc=sym.put(name());
        nest=loc.nest;
        index=loc.index;
    }


    @Override
    public Object eval(Environment env) {
        if (index==UNKNOWN){
            return env.get(name());
        }else if (nest==MemberSymbols.FIELD){
            return getThis(env).read(index);
        }else if (nest==MemberSymbols.METHOD){
            return getThis(env).method(index);
        }else {
            return env.get(nest,index);
        }
    }

    private OptStoneObject getThis(Environment env) {
       return (OptStoneObject) env.get(0,0);
    }

    public void  evalForAssign(Environment env,Object val){
        if (index==UNKNOWN){
            env.put(name(),val);
        }else if (nest==MemberSymbols.FIELD){
            getThis(env).write(index,val);
        }else if (nest==MemberSymbols.METHOD){
            throw new StoneExcetion("无法更新方法 "+name(),this);
        }else {
            env.put(nest,index,val);
        }
    }
}
