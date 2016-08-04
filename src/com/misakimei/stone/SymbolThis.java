package com.misakimei.stone;

/**
 * Created by 18754 on 2016/8/4.
 */
public class SymbolThis extends Symbols {
    public static final String name="this";
    public SymbolThis(Symbols out){
        super(out);
        add(name);
    }

    @Override
    public int putNew(String key) {
        throw new StoneExcetion("SymbolThis putNew 失败");
    }

    @Override
    public Location put(String key) {
        Location loc=outer.put(key);
        if (loc.nest>=0){
            loc.nest++;
        }
        return loc;
    }
}
