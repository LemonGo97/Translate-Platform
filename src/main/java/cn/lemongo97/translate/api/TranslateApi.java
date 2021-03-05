package cn.lemongo97.translate.api;


import cn.lemongo97.translate.model.BaiduTranslateResult;
import cn.lemongo97.translate.model.XunFeiTranslateResult;
import cn.lemongo97.translate.model.YouDaoTranslateResult;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface TranslateApi {

    String BAIDU_TRANSLATE_BASEURL = "https://fanyi-api.baidu.com";
    String YOUDAO_TRANSLATE_BASEURL = "https://openapi.youdao.com";
    String XUNFEI_TRANSLATE_BASEURL = "https://ntrans.xfyun.cn";

    @POST("/api/trans/vip/translate")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<BaiduTranslateResult> baiduTranslate(@QueryMap Map<String, String> requestBody);

    @POST("/api")
    Call<YouDaoTranslateResult> youdaoTranslate(@QueryMap Map<String, String> requestBody);

    @POST("/v2/ots")
    Call<XunFeiTranslateResult> xunfeiTranslate(@Body RequestBody body, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Host") String host, @Header("Date") String date, @Header("Digest") String digest, @Header("Authorization") String authorization);
}
