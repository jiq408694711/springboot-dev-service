package com.chengdu.jiq.common.dynamicdatasource;

/**
 * Created by jiyiqin on 2017/11/7.
 */
public class LookupKeyHolder {

//    private static final ThreadLocal<String> HOLDER = new ThreadLocal<String>(){};
    private static final ThreadLocal<String> HOLDER = new InheritableThreadLocal<String>();

    public static String getLookupKey() {
        return HOLDER.get();
    }

    public static void setLookupKey(String name) {
        HOLDER.set(name);
    }

    public static void removeLookupKey() {
        HOLDER.remove();
    }
}
