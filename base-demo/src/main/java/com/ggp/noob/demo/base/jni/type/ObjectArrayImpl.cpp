#include "jni.h"
#include "com_ggp_noob_demo_base_jni_type_ObjectArray.h"
#include <stdio.h>

/*
 * Class:     com_ggp_noob_demo_base_jni_type_ObjectArray
 * Method:    initInt2DArray
 * Signature: (I)[[I
 */
JNIEXPORT jobjectArray JNICALL Java_com_ggp_noob_demo_base_jni_type_ObjectArray_initInt2DArray
  (JNIEnv * env, jclass cls, jint size){
    jobjectArray result;
    int i;
    jclass intArrCls = env->FindClass("[I");
    if(intArrCls == NULL){
        return NULL;
    }
    result = env->NewObjectArray(size,intArrCls,NULL);
    for(i = 0; i < size; i++){
        jint tmp[256];
        int j;
        jintArray iarry = env->NewIntArray(size);
        if(iarry == NULL){
            return NULL;
        }
        for(j=0;j<size;j++){
            tmp[j] = i+j;
        }
        env->SetIntArrayRegion(iarry,0,size,tmp);
        env->SetObjectArrayElement(result,i,iarry);
        env->DeleteLocalRef(iarry);
    }
    return result;

  }