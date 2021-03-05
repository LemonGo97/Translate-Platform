package cn.lemongo97.translate.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static final Gson INSTANCE;

    static {
        INSTANCE = new GsonBuilder().disableHtmlEscaping().create();
    }

    private GsonUtil(){}

    public static Gson instance() {
        return INSTANCE;
    }

}
