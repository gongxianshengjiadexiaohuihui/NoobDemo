#include "jni.h"
#include "com_ggp_noob_demo_base_jni_type_IntArray.h"
#include <stdio.h>

/*
 * Class:     com_ggp_noob_demo_base_jni_type_IntArray
 * Method:    sumArray
 * Signature: ([I)I
 */
JNIEXPORT jint JNICALL Java_com_ggp_noob_demo_base_jni_type_IntArray_sumArray
  (JNIEnv *env, jobject obj, jintArray arr){
    jint *carr;
    jint i,len,sum=0;
    len = env->GetArrayLength(arr);
    carr = env->GetIntArrayElements(arr,NULL);
    if(carr == NULL){
       return 0;
    }
    for(i=0;i<len;i++){
        sum+=carr[i];
    }
    env->ReleaseIntArrayElements(arr,carr,0);
    return sum;
 }