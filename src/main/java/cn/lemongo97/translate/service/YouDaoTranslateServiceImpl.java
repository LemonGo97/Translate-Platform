package cn.lemongo97.translate.service;

import cn.hutool.crypto.digest.DigestUtil;

import cn.lemongo97.translate.api.TranslateApi;
import cn.lemongo97.translate.model.TranslateResult;
import cn.lemongo97.translate.model.YouDaoTranslateResult;
import cn.lemongo97.translate.util.GsonUtil;
import cn.lemongo97.translate.util.Retrofit2Util;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YouDaoTranslateServiceImpl implements TranslateService {
    @Override
    public String translate(String str, String appId, String securityKey) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TranslateApi.YOUDAO_TRANSLATE_BASEURL)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.instance()))
                .client(Retrofit2Util.getClient()).build();
        TranslateApi translateService = retrofit.create(TranslateApi.class);

        Map<String, String> query = getQueryParams(str, appId, securityKey);
        Call<YouDaoTranslateResult> responseBodyCall = translateService.youdaoTranslate(query);
        Response<YouDaoTranslateResult> execute = responseBodyCall.execute();
        TranslateResult result = execute.body();
        return result == null ? null : result.getResult();
    }

    private Map<String, String> getQueryParams(String query, String appId, String securityKey) {
        Map<String, String> params = new HashMap<>();
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "auto");
        params.put("to", "zh-CHS");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = appId + truncate(query) + salt + curtime + securityKey;
        String sign = DigestUtil.sha256Hex(signStr);
        params.put("appKey", appId);
        params.put("q", query);
        params.put("salt", salt);
        params.put("sign", sign);
        params.put("vocabId", "0");
        return params;
    }

    private String truncate(String str) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        return len <= 20 ? str : (str.substring(0, 10) + len + str.substring(len - 10, len));
    }
}
