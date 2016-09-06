package com.misakimei.stone.vm;

import com.misakimei.stone.*;
import com.misakimei.stone.tool.Log;

import java.util.ArrayList;

import static com.misakimei.stone.vm.Opcode.*;

/**
 * Created by 18754 on 2016/8/31.
 */
public class StoneVM {
    protected byte[] code;
    protected Object[] stack;
    protected String[] strings;
    protected HeapMemory heap;

    public int pc, fp, sp, ret;
    protected Object[] register;

    public static final int NUM_OF_REG = 6;

    public static final int SAVE_AREA_SIZE = NUM_OF_REG + 2;

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public StoneVM(int codesize, int stacksize, int stringsize, HeapMemory heap) {
        code = new byte[codesize];
        stack = new Object[stacksize];
        strings = new String[stacksize];
        register=new Object[NUM_OF_REG];
        this.heap = heap;
    }

    public void setReg(int i, Object value) {
        register[i] = value;
    }

    public String[] strings() {
        return strings;
    }

    public byte[] code() {
        return code;
    }

    public Object[] stack() {
        return stack;
    }

    public HeapMemory heap() {
        return heap;
    }

    public void run(int entry) {
        pc = entry;
        fp = 0;
        sp = 0;
        ret = -1;
        while (pc >= 0) {
            mainLoop();
        }
    }

    private void mainLoop() {
        switch (code[pc]) {
            case ICONST:
                register[decodeRegister(code[pc + 5])] = readInt(code, pc + 1);
                pc += 6;
                break;
            case BCONST:
                register[decodeRegister(code[pc + 2])] = (int) code[pc + 1];
                pc += 3;
                break;
            case SCONST:
                register[decodeRegister(code[pc + 3])] = strings[readShort(code, pc + 1)];
                pc += 4;
                break;
            case MOVE:
                moveValue();
                break;
            case GMOVE:
                moveHeapValue();
                break;
            case IFZERO:
                Object value = register[decodeRegister(code[pc + 1])];
                if (value instanceof Integer && ((Integer) value).intValue() == 0) {
                    pc += readShort(code, pc + 2);
                } else {
                    pc += 4;
                }
                break;
            case GOTO:
                pc += readShort(code, pc + 1);
                break;
            case CALL:
                callFunction();
                break;
            case RETURN:
                pc = ret;
                break;
            case SAVE:
                saveRegisters();
                break;
            case RESTORE:
                restoreRegisters();
                break;
            case NEG: {
                int reg = decodeRegister(code[pc + 1]);
                Object v = register[reg];
                if (v instanceof Integer) {
                    register[reg] = -((Integer) v).intValue();
                } else {
                    throw new StoneExcetion("操作符 - 错误");
                }
                pc += 2;
                break;
            }
            default:
                if (code[pc] > LESS) {
                    throw new StoneExcetion("错误");
                } else {
                    computerNumber();
                }
                break;
        }
    }

    protected void moveValue() {
        byte src = code[pc + 1];
        byte dest = code[pc + 2];
        Object value;
        if (isRegister(src)) {
            value = register[decodeRegister(src)];
        } else {
            value = stack[fp + decodeOffset(src)];//这里导致为null
        }

        if (isRegister(dest)) {
            register[decodeRegister(dest)] = value;
        } else {
            stack[fp + decodeOffset(dest)] = value;
        }
        pc += 3;

    }

    private void moveHeapValue() {
        byte rand = code[pc + 1];
        if (isRegister(rand)) {
            int dest = readShort(code, pc + 2);
            heap.write(dest, register[decodeRegister(rand)]);
        } else {
            int src = readShort(code, pc + 1);
            register[decodeRegister(code[pc + 3])] = heap.read(src);
        }
        pc += 4;
    }

    private void callFunction() {
        Object value = register[decodeRegister(code[pc + 1])];
        int numofArgs = code[pc + 2];
        if (value instanceof VMFunction && ((VMFunction) value).getParamters().size() == numofArgs) {
            ret = pc + 3;
            pc = ((VMFunction) value).entry();
        } else if (value instanceof NativeFunction && ((NativeFunction) value).numParams() == numofArgs) {
            Object[] args = new Object[numofArgs];
            for (int i = 0; i < numofArgs; i++) {
                args[i] = stack[sp + i];
            }
            stack[sp] = ((NativeFunction) value).invoke(args, new Arguments(new ArrayList<ASTree>()));
            pc += 3;
        } else {
            throw new StoneExcetion("函数调用错误");
        }
    }

    private void saveRegisters() {
        int size = decodeOffset(code[pc + 1]);
        int dest = size+sp;
        for (int i = 0; i < NUM_OF_REG; i++) {
            stack[dest++] = register[i];
        }
        stack[dest++] = fp;
        fp = sp;
        sp += size + SAVE_AREA_SIZE;
        stack[dest++] = ret;
        pc += 2;
    }

    private void restoreRegisters() {
        int dest = decodeOffset(code[pc + 1]) + fp;
        for (int i = 0; i < NUM_OF_REG; i++) {
            register[i] = stack[dest++];
        }
        sp = fp;
        fp = ((Integer) stack[dest++]).intValue();
        ret = ((Integer) stack[dest++]).intValue();
        pc += 2;
    }

    private void computerNumber() {
        int left = decodeRegister(code[pc + 1]);
        int right = decodeRegister(code[pc + 2]);
        Object v1 = register[left];
        Object v2 = register[right];
        boolean arenum = (v1 instanceof Integer) && (v2 instanceof Integer);
        if (code[pc] == ADD && !arenum) {
            register[left] = String.valueOf(v1) + String.valueOf(v2);
        } else if (code[pc] == EQUAL && !arenum) {
            if (v1 == null) {
                register[left] = (v2 == null ? TRUE : FALSE);
            } else {
                register[left] = v1.equals(v2) ? TRUE : FALSE;
            }
        }else {
            if (!arenum){throw new StoneExcetion("无法使用此操作符 非数字");}

            int i1=((Integer)v1).intValue();
            int i2=((Integer)v2).intValue();
            int i3;

            switch (code[pc]){
                case ADD:
                    i3=i1+i2;
                    break;
                case SUB:
                    i3=i1-i2;
                    break;
                case MUL:
                    i3=i1*i2;
                    break;
                case DIV:
                    i3=i1/i2;//T_T 哼~~ 我比较喜欢浮点数
                    break;
                case REM:
                    i3=i1%i2;
                    break;
                case EQUAL:
                    i3=i1==i2?TRUE:FALSE;
                    break;
                case MORE:
                    i3=i1>i2?TRUE:FALSE;
                    break;
                case LESS:
                    i3=i1<i2?TRUE:FALSE;
                    break;
                default:
                    throw new StoneExcetion("处理操作符 错误 找不到操作符 "+code[pc]);
            }
            register[left]=i3;
        }
        pc+=3;
    }

    public static int readShort(byte[] array, int index) {
        return (array[index]<<8)|(array[index+1]&0xff);
    }

    public static int readInt(byte[] array, int index) {
        return (array[index]<<24)
                |((array[index+1]&0xff)<<16)
                |((array[index+2]&0xff)<<8)
                |array[index+3]&0xff;
    }
}
