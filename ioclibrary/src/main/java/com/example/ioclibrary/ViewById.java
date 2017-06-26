package com.example.ioclibrary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by XiaopengWang on 2017/5/19.
 * Email:xiaopeng.wang@qaii.ac.cn
 * QQ:839853185
 * WinXin;wxp19940505
 */
@Target(ElementType.FIELD) // 属性
@Retention(RetentionPolicy.RUNTIME)//运行时生效，CLASS生效编译时
public @interface ViewById {
    int value();
}
