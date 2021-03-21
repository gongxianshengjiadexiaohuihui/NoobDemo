# 生成头文件
 1、进入到java目录
 javah -classpath .  com.ggp.noob.demo.base.jni.JniHelloWorld

# C++ 中include头文件时尖括号<>与双引号""的区别

简单区分：

1，系统自带的头文件用尖括号括起来，这样编译器会在系统文件目录下查找。 

## include <xxx.h> 
2， 用户自定义的文件用双引号括起来，编译器首先会在用户目录下查找，然后在到C++安装目录（比如VC中可以指定和修改库文件查找路径，Unix和Linux中可以通过环境变量来设定）中查找，最后在系统文件中查找。 
## include “xxx.h”

# C++ 和 c 指针使用区别
指针写法    类型   *name
## c
(*env)->FindClass( ...)
## c++
env->FindClass(...)

# C++和 c在重载方法时，必须和原方法名一模一样，一个空格都不能少


# Windows编译
 1、安装MinGW
 2、编译
 gcc -I $JAVA_HOME/include  -I $JAVA_HOME/include/win32  -I .  -shared JniHelloWorldImpl.cpp  -o JniHelloWorld.dll
  

# Linux编译
 gcc -fPIC -I $JAVA_HOME/include  -I $JAVA_HOME/include/linux  -I .  -shared JniHelloWorldImpl.cpp  -o libJniHelloWorld.so
 
 ##
 linux的so必须是以lib开头的，但是引用的时候不需要加上
 
 ## 
 LD_LIBRARY_PATH环境变量是用来设置本地库路径的
 
## relocation R_X86_64_32 against `.rodata' 
 -fPIC 作用于编译阶段，告诉编译器产生与位置无关代码(Position-Independent Code)，
 则产生的代码中，没有绝对地址，全部使用相对地址，故而代码可以被加载器加载到内存的任意位置，都可以正确的执行。这正是共享库所要求的，共享库被加载时，在内存的位置不是固定的。
 PIC就是position independent code.
 PIC使.so文件的代码段变为真正意义上的共享.
 如果不加-fPIC,则加载.so文件的代码段时,代码段引用的数据对象需要重定位, 重定位会修改代码段的内容,这就造成每个使用这个.so文件代码段的进程在内核里都会生成这个.so文件代码段的copy.每个copy都不一样,取决于 这个.so文件代码段和数据段内存映射的位置.
 
##  error trying to exec cc1plus
 第一，没有安装g++
 第二，gcc的版本和g++版本不相符合是因为没有安装g++安装包
 
 安装g++安装包及依赖包：
 
 sudo yum install gcc-c++
 
 # 运行
   要在包的根目录，执行
   java  com.ggp.noob.demo.base.jni.JniHelloWorld
   