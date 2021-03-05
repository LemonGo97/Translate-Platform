package cn.lemongo97.translate.constants;

public enum TranslateSourceEnum {
    BAIDU("cn.lemongo97.translate.service.BaiduTranslateServiceImpl"),
    YOUDAO("cn.lemongo97.translate.service.YouDaoTranslateServiceImpl"),
    XUNFEI("cn.lemongo97.translate.service.XunFeiTranslateServiceImpl");

    private String clazz;

    TranslateSourceEnum(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz(){
        return this.clazz;
    }
}
