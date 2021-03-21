#include "jni.h"
#include "com_ggp_noob_demo_base_jni_JniHelloWorld.h"
#include <stdio.h>

/*
 * Class:     com_ggp_noob_demo_base_jni_JniHelloWorld
 * Method:    sayHelloWorld
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ggp_noob_demo_base_jni_JniHelloWorld_sayHelloWorld
  (JNIEnv *, jobject){
       printf("hello world!\n");
       return;
 }