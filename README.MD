## 翻译平台对接

采用 Java 语言实现

其他不多讲，都在代码里

目前已对接以下翻译平台，将相关平台生成的 `APIKey` 、 `SecurityKey`之类的填进去即可：

- 百度
- 有道
- 讯飞

使用方式参照 `Translate.java` 类

> 讯飞的比较特殊些，采用了三个参数进行验证，解决方案是拼接起来两个参数