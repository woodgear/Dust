package com.misakimei.stone.type;

import com.misakimei.stone.ASTree;
import com.misakimei.stone.NULLStmnt;
import com.misakimei.stone.tool.Log;

/**
 * Created by 18754 on 2016/9/12.
 */
public class TypeInfo {
    public static final TypeInfo ANY = new TypeInfo() {
        @Override
        public String toString() {
            return "Any";
        }
    };
    public static final TypeInfo INT = new TypeInfo() {
        @Override
        public String toString() {
            return "Int";
        }
    };
    public static final TypeInfo STRING = new TypeInfo() {
        @Override
        public String toString() {
            return "String";
        }
    };


    public TypeInfo type() {
        return this;
    }

    public boolean match(TypeInfo obj) {
        return type() == obj.type();
    }

    public boolean subtypeof(TypeInfo supertype) {
        supertype = supertype.type();
        return type() == supertype || supertype == ANY;
    }

    public void assertSubtypeOf(TypeInfo type, TypeEnv env, ASTree where) throws TypeException {

        if (type.isUnknowType()) {
            type.toUnknowType().assertSubtypeOf(this, env, where);
        } else if (!subtypeof(type)) {
            throw new TypeException("无法将类型 从 " + this + " 转换至 " + type, where);
        }
    }

    public TypeInfo union(TypeInfo right, TypeEnv tenv) {
        if (right.isUnknowType()) {
            return right.union(this, tenv);
        } else if (match(right)) {
            return type();
        } else {
            return ANY;
        }
    }

    public TypeInfo plus(TypeInfo right, TypeEnv tenv) {
        if (right.isUnknowType()) {
            return right.plus(this, tenv);
        } else if (INT.match(this) && INT.match(right)) {
            return INT;
        } else if (STRING.match(this) && STRING.match(right)) {
            return STRING;
        }
        return ANY;
    }

    public static TypeInfo get(TypeTag tag) throws TypeException {
        String name = tag.type();

        if (INT.toString().equals(name)) {
            return INT;
        } else if (STRING.toString().equals(name)) {
            return STRING;
        } else if (ANY.toString().equals(name)) {
            return ANY;
        } else if (TypeTag.UNDEF.equals(name)) {
            return new UnknownType();
        } else {
            throw new TypeException("未知的类型 " + name, tag);
        }
    }

    public boolean isUnknowType() {
        return false;
    }

    public UnknownType toUnknowType() {
        return null;
    }

    public boolean isFuncitonType() {
        return false;
    }

    public FuncitonType toFuncitonType() {
        return null;
    }

    public static FuncitonType function(TypeInfo retType, TypeInfo... params) {
        return new FuncitonType(retType, params);
    }

    public static class UnknownType extends TypeInfo {

        protected TypeInfo type = null;

        public boolean resloved() {
            return type != null;
        }

        public void setType(TypeInfo t) {
            type = t;
        }


        @Override
        public void assertSubtypeOf(TypeInfo type, TypeEnv env, ASTree where) throws TypeException {
            if (resloved()){
                type.assertSubtypeOf(type,env,where);
            }else {
                env.addEquation(this,type);
            }
        }


        @Override
        public TypeInfo union(TypeInfo right, TypeEnv tenv) {
            if (resloved()){
                return type.union(right,tenv);
            }else {
                tenv.addEquation(this,right);
                return right;
            }
        }

        @Override
        public TypeInfo plus(TypeInfo right, TypeEnv tenv) {
            if (resloved()){
                return right.plus(this,tenv);
            }else {
                tenv.addEquation(this,right);
                return right.plus(INT,tenv);
            }
        }

        @Override
        public TypeInfo type() {
            return ANY;
        }

        @Override
        public String toString() {
            return type().toString();
        }

        @Override
        public boolean isUnknowType() {
            return true;
        }

        @Override
        public UnknownType toUnknowType() {
            return this;
        }
    }


    public static class FuncitonType extends TypeInfo {
        public TypeInfo returntype;
        public TypeInfo[] parameterTypes;

        public FuncitonType(TypeInfo ret, TypeInfo... params) {
            returntype = ret;
            parameterTypes = params;
        }

        @Override
        public boolean isFuncitonType() {
            return true;
        }

        @Override
        public FuncitonType toFuncitonType() {
            return this;
        }

        @Override
        public boolean match(TypeInfo obj) {
            if (!(obj instanceof FuncitonType)) {
                return false;
            }
            FuncitonType func = (FuncitonType) obj;
            if (parameterTypes.length != func.parameterTypes.length) {
                return false;
            }
            for (int i = 0; i < parameterTypes.length; i++) {
                if (!parameterTypes[i].match(func.parameterTypes[i])) {
                    return false;
                }
            }
            return returntype.match(func.returntype);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (parameterTypes.length == 0) {
                sb.append("Unit");
            } else {
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i > 0) {
                        sb.append("*");
                    }
                    sb.append(parameterTypes[i]);
                }
            }
            sb.append("-> ").append(returntype);
            return sb.toString();
        }
    }
}
