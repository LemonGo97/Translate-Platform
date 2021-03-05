package cn.lemongo97.translate.service;

import cn.hutool.core.codec.Base64;
import cn.lemongo97.translate.api.TranslateApi;
import cn.lemongo97.translate.model.TranslateResult;
import cn.lemongo97.translate.model.XunFeiTranslateResult;
import cn.lemongo97.translate.util.GsonUtil;
import cn.lemongo97.translate.util.Retrofit2Util;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 讯飞的比较特殊，采用了三个参数用于认证
 * <p>
 * 现将apiKey和apiSecret拼接字符串存储使用
 * example: apiKey;apiSecret
 */
public class XunFeiTranslateServiceImpl implements TranslateService {

    private static final String WebOTS_URL = "https://ntrans.xfyun.cn/v2/ots";

    @Override
    public String translate(String str, String appId, String securityKey) throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TranslateApi.XUNFEI_TRANSLATE_BASEURL)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.instance()))
                .client(Retrofit2Util.getClient()).build();
        TranslateApi translateService = retrofit.create(TranslateApi.class);

        String body = getQueryParams("this is two question", appId);

        String apiKey = securityKey.substring(0, securityKey.indexOf(';'));
        String apiSecret = securityKey.substring(securityKey.indexOf(';') + 1);

        String date = getDate();
        String digest = getDigest(body);
        Call<XunFeiTranslateResult> responseBodyCall = translateService
                .xunfeiTranslate(RequestBody.create(MediaType.parse("application/json"), body),
                "application/json",
                "application/json,version=1.0",
                "ntrans.xfyun.cn",
                date,
                digest,
                getAuthorization(date, digest, apiKey, apiSecret));

        Response<XunFeiTranslateResult> execute = responseBodyCall.execute();
        TranslateResult result = execute.body();
        return result == null ? null : result.getResult();
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return format.format(date);
    }

    private String getDigest(String body) {
        return "SHA-256=" + signBody(body);
    }

    private String getAuthorization(String date, String digest, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(WebOTS_URL);
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
                append("date: ").append(date).append("\n").//
                append("POST ").append(url.getPath()).append(" HTTP/1.1").append("\n").//
                append("digest: ").append(digest);
        //System.out.println("【OTS WebAPI builder】\n" + builder);
        String sha = hmacsign(builder.toString(), apiSecret);
        //System.out.println("【OTS WebAPI sha】\n" + sha);

        //组装authorization
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line digest", sha);
        return authorization;
    }

    private String signBody(String body) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(body.getBytes(StandardCharsets.UTF_8));
            encodestr = Base64.encode(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    private String getQueryParams(String query, String appId) {
        JsonObject body = new JsonObject();
        JsonObject business = new JsonObject();
        JsonObject common = new JsonObject();
        JsonObject data = new JsonObject();
        //填充common
        common.addProperty("app_id", appId);
        //填充business
        business.addProperty("from", "auto");
        business.addProperty("to", "cn");
        //填充data
        //System.out.println("【OTS WebAPI TEXT字个数：】\n" + TEXT.length());
        String textBase64 = Base64.encode(query);
        //System.out.println("【OTS WebAPI textBase64编码后长度：】\n" + textBase64.length());
        data.addProperty("text", textBase64);
        //填充body
        body.add("common", common);
        body.add("business", business);
        body.add("data", data);
        return body.toString();
    }

    /**
     * hmacsha256加密
     */
    private String hmacsign(String signature, String apiSecret) throws Exception {
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(signature.getBytes(StandardCharsets.UTF_8));
        return Base64.encode(hexDigits);
    }
}
