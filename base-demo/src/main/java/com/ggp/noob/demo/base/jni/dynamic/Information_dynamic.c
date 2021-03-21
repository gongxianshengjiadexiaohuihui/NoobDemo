#include<jni.h>
#include<stdio.h>

void native_name(JNIEnv *env,jobject obj){
    printf("name:ggp");
}
void native_addr(JNIEnv *env,jobject obj){
    printf("addr:china");
}
const static JNINativeMethod gMethods[]={
    "name","()V",(void *)native_name,
    "addr","()V",(void *)native_addr
};

static jclass myClass;
static const char* const ClassName = "com/ggp/noob/demo/base/jni/Information";

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved){
    printf("------JNI_OnLoad begin---------\n");
    JNIEnv* env = NULL;
    jint result = -1;
    if((*vm)->GetEnv(vm,(void **)&env,JNI_VERSION_1_8) != JNI_OK){
      return -1;
    }

    myClass = (*env)->FindClass(env,ClassName);
    if(myClass == NULL){
       printf("Can not find class:%s.\n",ClassName);
       return -1;
    }
    printf("------JNI_OnLoad load class success---------\n");
    if((*env)->RegisterNatives(env,myClass,gMethods,sizeof(gMethods)/sizeof(gMethods[0]))){
        printf("Register native methods error.\n");
        return -1;
    }
    printf("------JNI_OnLoad Success---------\n");
    return JNI_VERSION_1_8;
}