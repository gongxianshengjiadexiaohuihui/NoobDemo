#include<stdio.h>
#include"jni.h"
#include"com_ggp_noob_demo_base_jni_type_Prompt.h"

/*
 * Class:     com_ggp_noob_demo_base_jni_type_Prompt
 * Method:    getLine
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ggp_noob_demo_base_jni_type_Prompt_getLine
  (JNIEnv *env, jobject obj, jstring prompt){
      char buf[128];
      const char *str;
      str = env->GetStringUTFChars(prompt,NULL);
      /*
       *不要忘记检查GetStringUTFChars的返回值，这是因为Java虚拟机的实现决定内部需要申请内存来容纳UTF-8字符串，内存的申请是有可能会失败的。如果内存申请失败，
       *那么GetStringUTFChars将会返回NULL并且会抛出OutOfMemoryError异常。正如我们会在第六章介绍的一样，在JNI中抛出异常和在Java中抛出异常是不同的。
       *通过JNI抛出的挂起异常不会自动更改本地C代码的控制流程。相反我们需要一个显示的return语句来跳过C函数中的剩余语句。Java_Prompt_getLine返回后，该异常会返回给Prompt.getLine的调用者Prompt.main函数中。
       */
      if(str == NULL){
        return NULL;
      }
      printf("%s",str);
      //刷新缓冲区~ printf是按行刷新的
      fflush(stdout);
      env->ReleaseStringUTFChars(prompt,str);
      scanf("%s",buf);
      return env->NewStringUTF(buf);
  }