package cn.lemongo97.translate.model;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.UnicodeUtil;
import com.google.common.base.Strings;

import java.util.List;
import java.util.StringJoiner;

public class BaiduTranslateResult implements TranslateResult {
    private String error_code;
    private String error_msg;
    private String from;
    private String to;
    private List<Trans_result> trans_result;

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTrans_result(List<Trans_result> trans_result) {
        this.trans_result = trans_result;
    }

    public List<Trans_result> getTrans_result() {
        return trans_result;
    }

    @Override
    public String getResult() {
        if (!Strings.isNullOrEmpty(error_code) || !Strings.isNullOrEmpty(error_msg) || CollectionUtil.isEmpty(trans_result)) {
            return null;
        }
        return UnicodeUtil.toString(trans_result.get(0).dst);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaiduTranslateResult.class.getSimpleName() + "[", "]")
                .add("error_code='" + error_code + "'")
                .add("error_msg='" + error_msg + "'")
                .add("from='" + from + "'")
                .add("to='" + to + "'")
                .add("trans_result=" + trans_result)
                .toString();
    }

    public static class Trans_result {

        private String src;
        private String dst;

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSrc() {
            return src;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getDst() {
            return dst;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Trans_result.class.getSimpleName() + "[", "]")
                    .add("src='" + src + "'")
                    .add("dst='" + dst + "'")
                    .toString();
        }
    }
}
