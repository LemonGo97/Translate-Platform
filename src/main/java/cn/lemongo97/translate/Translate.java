package cn.lemongo97.translate;


import cn.lemongo97.translate.constants.TranslateSourceEnum;
import cn.lemongo97.translate.service.TranslateInstanceFactory;
import cn.lemongo97.translate.service.TranslateService;

public class Translate {

    public static void main(String[] args) throws Exception {
        howToUse();
    }

    private static void howToUse() throws Exception {
        TranslateSourceEnum translateSourceEnum = TranslateSourceEnum.valueOf("XUNFEI");
        TranslateService instance = TranslateInstanceFactory.getInstance(translateSourceEnum);
        // 讯飞的比较特殊，采用了三个参数用于认证
        // 现将apiKey和apiSecret拼接字符串存储使用
        // example: securityKey = apiKey;apiSecret
        String result = instance.translate("this is two question", "", "");
        System.out.println(result);
    }

}
