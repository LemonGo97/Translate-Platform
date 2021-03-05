package cn.lemongo97.translate.service;


import cn.lemongo97.translate.constants.TranslateSourceEnum;

import java.util.HashMap;
import java.util.Map;

public class TranslateInstanceFactory {
    private static final Map<TranslateSourceEnum, TranslateService> instances = new HashMap<>();

    static {
        for (TranslateSourceEnum value : TranslateSourceEnum.values()) {
            try {
                TranslateService translateService = (TranslateService) Class.forName(value.getClazz()).newInstance();
                instances.put(value, translateService);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private TranslateInstanceFactory() {
    }

    public static TranslateService getInstance(TranslateSourceEnum key) {
        return instances.get(key);
    }
}
