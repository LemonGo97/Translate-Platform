package cn.lemongo97.translate.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.UnicodeUtil;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class YouDaoTranslateResult implements TranslateResult {

    private String tSpeakUrl;
    private String requestId;
    private String query;
    private List<String> translation;
    private String errorCode;
    private Dict dict;
    private String l;
    private boolean isWord;
    private String speakUrl;

    public void setTSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getTSpeakUrl() {
        return tSpeakUrl;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public Dict getDict() {
        return dict;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getL() {
        return l;
    }

    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }

    public boolean getIsWord() {
        return isWord;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    @Override
    public String getResult() {
        if (!Objects.equals("0", errorCode) || CollectionUtil.isEmpty(translation)) {
            return null;
        }
        return UnicodeUtil.toString(translation.get(0));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", YouDaoTranslateResult.class.getSimpleName() + "[", "]")
                .add("tSpeakUrl='" + tSpeakUrl + "'")
                .add("requestId='" + requestId + "'")
                .add("query='" + query + "'")
                .add("translation=" + translation)
                .add("errorCode='" + errorCode + "'")
                .add("dict=" + dict)
                .add("l='" + l + "'")
                .add("isWord=" + isWord)
                .add("speakUrl='" + speakUrl + "'")
                .toString();
    }
}
