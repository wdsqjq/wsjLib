package per.wsj.kotlinapp.net;

import java.util.List;

/**
 * Description:
 * Created by wangsj on 17-12-19.
 */

public class ArticleResponse {

    /**
     * msg : success
     * code : 200
     * detail : [{"name":"Kotlin_简介","url":"0.html"},{"name":"IntelliJ IDEA环境搭建","url":"1.html"},{"name":"Eclipse环境搭建","url":"2.html"},{"name":"使用命令行编译","url":"base3.html"},{"name":"Android环境搭建","url":"4.html"},{"name":"基础语法","url":"5.html"},{"name":"基本数据类型","url":"6.html"},{"name":"条件控制","url":"7.html"},{"name":"循环控制","url":"8.html"},{"name":"类和对象","url":"9.html"},{"name":"继承","url":"10.html"},{"name":"接口","url":"11.html"},{"name":"扩展","url":"12.html"},{"name":"数据类与密封类","url":"13.html"},{"name":"泛型","url":"14.html"},{"name":"枚举类","url":"15.html"},{"name":"对象表达式与声明","url":"16.html"},{"name":"委托","url":"17.html"}]
     */

    private String msg;
    private String code;
    private List<DetailBean> detail;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * name : Kotlin_简介
         * url : 0.html
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", detail=" + detail +
                '}';
    }
}
