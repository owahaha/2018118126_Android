package com.example.emumeration;

import java.io.Serializable;
//多选数量的模式
public enum MultipleNumModel implements Serializable {
    FULL_CLEAR, //超过数量清除所有
    FULL_REMOVE_FIRST //超过数量清除第一个
}
