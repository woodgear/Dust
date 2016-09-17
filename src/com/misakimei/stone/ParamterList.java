package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.type.TypeTag;
import com.misakimei.stone.vm.EnvEx;
import com.misakimei.stone.vm.StoneVM;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public class ParamterList extends ASTList {

    protected int[] offsets;//name的index

    public ParamterList(List<ASTree> lis) {
        super(lis);
    }

    public String name(int i) {
        return ((ASTLeaf) child(i).child(0)).token().getText();
    }

    public int size() {
        return numChildren();
    }

    public TypeTag typeTag(int i) {
        return (TypeTag) child(i).child(1);
    }

    @Override
    public void lookup(Symbols symbol) {
        int s = size();
        offsets = new int[s];
        for (int i = 0; i < s; i++) {
            offsets[i] = symbol.putNew(name(i));
        }
    }

    public void eval(Environment env, int index, Object val) {
        env.put(0,offsets[index],val);
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        return super.typecheck(tenv);
    }

    public TypeInfo[] types() {
        int s=size();
        TypeInfo[]result=new TypeInfo[s];
        for (int i=0;i<s;i++){
            result[i]=TypeInfo.get(typeTag(i));
        }
        return result;
    }
}
