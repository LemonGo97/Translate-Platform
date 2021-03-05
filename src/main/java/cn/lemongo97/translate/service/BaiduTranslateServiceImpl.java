package cn.lemongo97.translate.service;

import cn.hutool.crypto.digest.MD5;
import cn.lemongo97.translate.api.TranslateApi;
import cn.lemongo97.translate.model.BaiduTranslateResult;
import cn.lemongo97.translate.model.TranslateResult;
import cn.lemongo97.translate.util.GsonUtil;
import cn.lemongo97.translate.util.Retrofit2Util;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class BaiduTranslateServiceImpl implements TranslateService {

    @Override
    public String translate(String str, String appId, String securityKey) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TranslateApi.BAIDU_TRANSLATE_BASEURL)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.instance()))
                .client(Retrofit2Util.getClient()).build();
        TranslateApi translateService = retrofit.create(TranslateApi.class);

        Map<String, String> query = getQueryParams(str, appId, securityKey);
        Call<BaiduTranslateResult> responseBodyCall = translateService.baiduTranslate(query);
        Response<BaiduTranslateResult> execute = responseBodyCall.execute();
        TranslateResult result = execute.body();
        return result == null ? null : result.getResult();
    }

    private Map<String, String> getQueryParams(String query, String appId, String securityKey) {
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("from", "auto");
        params.put("to", "zh");

        params.put("appid", appId);

        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        String src = appId + query + salt + securityKey;
        params.put("sign", MD5.create().digestHex(src));
        return params;
    }
}
