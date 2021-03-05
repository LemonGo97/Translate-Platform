package cn.lemongo97.translate.model;


import java.util.Objects;
import java.util.StringJoiner;

public class XunFeiTranslateResult implements TranslateResult {

    private int code;
    private Data data;
    private String message;
    private String sid;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", XunFeiTranslateResult.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("data=" + data)
                .add("message='" + message + "'")
                .add("sid='" + sid + "'")
                .toString();
    }

    public static class Data {

        private Result result;

        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Data.class.getSimpleName() + "[", "]")
                    .add("result=" + result)
                    .toString();
        }

        public static class Result {

            private String from;
            private String to;
            private Trans_result trans_result;

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

            public void setTrans_result(Trans_result trans_result) {
                this.trans_result = trans_result;
            }

            public Trans_result getTrans_result() {
                return trans_result;
            }

            public static class Trans_result {

                private String dst;
                private String src;

                public void setDst(String dst) {
                    this.dst = dst;
                }

                public String getDst() {
                    return dst;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getSrc() {
                    return src;
                }

                @Override
                public String toString() {
                    return new StringJoiner(", ", Trans_result.class.getSimpleName() + "[", "]")
                            .add("dst='" + dst + "'")
                            .add("src='" + src + "'")
                            .toString();
                }
            }

        }

    }

    @Override
    public String getResult() {
        if (!Objects.equals(0, code) || data == null || data.result == null || data.result.trans_result == null) {
            return null;
        }
        return data.result.trans_result.dst;
    }
}
