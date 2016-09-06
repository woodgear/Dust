package com.misakimei.stone.vm;

/**
 * Created by 18754 on 2016/9/6.
 */
public class Code {
   public StoneVM svm;
   public int codesize;
   public int numofStrings;
   public int nextReg;
   public int frameSize;

    public Code(StoneVM svm) {
        this.svm = svm;
        codesize = 0;
        numofStrings = 0;
    }

    public int position() {
        return codesize;
    }

    public void set(short value, int pos) {
        svm.code()[pos] = (byte) (value >>> 8);
        svm.code()[pos + 1] = (byte) value;
    }
    public void add(short s){
        add((byte)(s>>8));
        add((byte)s);
    }
    public void add(int i) {
        add((byte) (i >>> 24));
        add((byte) (i >>> 16));
        add((byte) (i >>> 8));
        add((byte) i);
    }
    public void add(byte b){
        svm.code()[codesize++]=b;
    }


    public int record(String s){
        svm.strings()[numofStrings]=s;
        return numofStrings++;
    }
}
