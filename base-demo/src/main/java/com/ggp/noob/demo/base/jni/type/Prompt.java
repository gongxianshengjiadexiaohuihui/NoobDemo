package com.ggp.noob.demo.base.jni.type;

/**
 * @Author:ggp
 * @Date:2021/3/17 21:40
 * @Description:
 */
public class Prompt {
    static {
        System.loadLibrary("Prompt");
    }

    private native String getLine(String prompt);

    public static void main(String[] args) {
        Prompt prompt = new Prompt();
        String input = prompt.getLine("Type a line: ");
        System.out.println("User typed:" + input);
    }

}
