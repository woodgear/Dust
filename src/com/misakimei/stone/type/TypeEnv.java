package com.misakimei.stone.type;

import com.misakimei.stone.StoneExcetion;

import java.util.Arrays;

/**
 * Created by 18754 on 2016/9/12.
 */
public class TypeEnv {
    protected TypeEnv outer;
    protected TypeInfo[]types;
    public TypeEnv(){
        this(8,null);
    }

    public TypeEnv(int size, TypeEnv out) {
        outer=out;
        types=new TypeInfo[size];
    }


    public TypeInfo get(int nest, int index) {
        if (nest==0){
            if (index<types.length){
                return types[index];
            }else {
                return null;
            }
        }else  if(outer==null){
            return null;
        }else {
            return outer.get(nest-1,index);
        }
    }

    public TypeInfo put(int nest, int index, TypeInfo valueType) {
        TypeInfo oldval;
        if (nest==0){
            access(index);
            oldval=types[index];
            types[index]=valueType;
            return oldval;
        }else if (outer==null){
            throw new StoneExcetion("找不到外部环境 ");
        }else {
            return outer.put(nest-1,index,valueType);
        }
    }

    private void access(int index) {
        if (index> types.length){
            int newlen=types.length*2;
            if (index>=newlen){
                newlen=index+1;
            }
            types= Arrays.copyOf(types,newlen);
        }
    }
}
