package com.misakimei.stone;

/**
 * Created by 18754 on 2016/8/4.
 */
public class MemberSymbols extends Symbols {
    public static int METHOD=-1;
    public static int FIELD=-2;
    protected int type;
    public MemberSymbols(Symbols outer,int type){
        super(outer);
        this.type=type;
    }

    @Override
    protected Location get(String key, int nest) {
        Integer index=table.get(key);
        if (index==null){
            if (outer==null){
                return null;
            }else {
                return outer.get(key,nest);
            }
        }else {
            return new Location(type,index);
        }
    }

    @Override
    public Location put(String key) {
        Location loc=get(key,0);
        if (loc==null){
            return new Location(type,add(key));
        }else {
            return loc;
        }

    }
}
