package com.misakimei.stone.type;

import com.misakimei.stone.ASTree;

/**
 * Created by 18754 on 2016/9/17.
 */
public class ToJava {
    public static final String METHOD="m";
    public static final String LOCAL="v";
    public static final String ENV="env";
    public static final String RESULT="res";
    public static final String ENV_TYPE="com.misakimei.stone.ArrayEnv";


    public static String transloateExpr(ASTree ast,TypeInfo from,TypeInfo to){
        return transloateExpr(ast.translate(null),from,to);
    }
    public static String transloateExpr(String expr,TypeInfo from,TypeInfo to){
        from=from.type();
        to=to.type();
        if (from==TypeInfo.INT){
            if (to==TypeInfo.ANY){
                return "new Integer("+expr+")";
            }else if (to==TypeInfo.STRING){
                return "Integer.toString("+expr+")";
            }
        }else if (from==TypeInfo.ANY){
            if (to==TypeInfo.STRING){
                return expr+".toString()";
            }else if (to==TypeInfo.INT){
                return "((Integer)"+expr+").intValue()";
            }
        }
        return expr;
    }
    public static String returnZero(TypeInfo to){
        if (to.type()==TypeInfo.ANY){
            return RESULT+"=new Integer(0);";
        }else {
            return RESULT+"=0";
        }
    }

}
