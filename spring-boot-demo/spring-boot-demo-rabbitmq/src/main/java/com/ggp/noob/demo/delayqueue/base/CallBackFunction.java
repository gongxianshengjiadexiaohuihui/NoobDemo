package com.ggp.noob.demo.delayqueue.base;

import java.io.Serializable;

/**
 * @Author Created by gongguanpeng on 2022/3/1 15:39
 */
@FunctionalInterface
public interface CallBackFunction<T> extends Serializable {

    void accept(T t);
}
