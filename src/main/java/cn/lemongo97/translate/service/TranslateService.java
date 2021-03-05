package cn.lemongo97.translate.service;

public interface TranslateService {
    String translate(String str, String appId, String securityKey) throws Exception;
}
