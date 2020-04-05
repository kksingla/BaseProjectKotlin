package com.appringer.common.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GSONUtils {
    public GSONUtils() {
    }

    public static String toString(Object obj) {
        return (new Gson()).toJson(obj);
    }

    public static <T> T getObj(String s, Class<T> aClass) {
        return (new Gson()).fromJson(s, aClass);
    }

    public static <T> T getObj(String s, Type type) {
        return (new Gson()).fromJson(s, type);
    }

    public static <T> T copy(Object src, Type destType) {
        return getObj(toString(src), destType);
    }
}
