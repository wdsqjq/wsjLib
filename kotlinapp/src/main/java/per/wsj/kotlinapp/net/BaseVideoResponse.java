package per.wsj.kotlinapp.net;

import java.util.List;

/**
 * Description:
 * Created by wangsj on 17-12-19.
 */

public class BaseVideoResponse {


    /**
     * msg : success
     * code : 200
     * detail : [{"preview":"video/thumb/base04.jpg","title":"你好世界","url":"video/base04.mp4"},{"preview":"video/thumb/base05.jpg","title":"变量与输出","url":"video/base05.mp4"},{"preview":"video/thumb/base06.jpg","title":"二进制基础","url":"video/base06.mp4"},{"preview":"video/thumb/base07.jpg","title":"变量和常亮及类型推断","url":"video/base07.mp4"},{"preview":"video/thumb/base08.jpg","title":"变量取值范围","url":"video/base08.mp4"},{"preview":"video/thumb/base09.jpg","title":"函数入门","url":"video/base09.mp4"},{"preview":"video/thumb/base10.jpg","title":"boolean类型","url":"video/base10.mp4"},{"preview":"video/thumb/base11.jpg","title":"命令行交互式终端","url":"video/base11.mp4"},{"preview":"video/thumb/base12.jpg","title":"函数加强","url":"video/base12.mp4"},{"preview":"video/thumb/base13.jpg","title":"函数作业详解","url":"video/base13.mp4"},{"preview":"video/thumb/base14.jpg","title":"字符串模板","url":"video/base14.mp4"},{"preview":"video/thumb/base15.jpg","title":"条件控制if和else","url":"video/base15.mp4"},{"preview":"video/thumb/base16.jpg","title":"字符串比较","url":"video/base16.mp4"},{"preview":"video/thumb/base17.jpg","title":"空值处理","url":"video/base17.mp4"},{"preview":"video/thumb/base18.jpg","title":"when表达式","url":"video/base18.mp4"},{"preview":"video/thumb/base19.jpg","title":"loop和Range","url":"video/base19.mp4"},{"preview":"video/thumb/base20.jpg","title":"list和map入门","url":"video/base20.mp4"},{"preview":"video/thumb/base21.jpg","title":"函数和函数表达式","url":"video/base21.mp4"},{"preview":"video/thumb/base22.jpg","title":"默认参数和具名参数","url":"video/base22.mp4"},{"preview":"video/thumb/base23.jpg","title":"字符串和数字之间的转换","url":"video/base23.mp4"},{"preview":"video/thumb/base24.jpg","title":"人机交互","url":"video/base24.mp4"},{"preview":"video/thumb/base25.jpg","title":"异常处理","url":"video/base25.mp4"},{"preview":"video/thumb/base26.jpg","title":"递归","url":"video/base26.mp4"},{"preview":"video/thumb/base27.jpg","title":"尾递归优化","url":"video/base27.mp4"},{"preview":"video/thumb/base28.jpg","title":"idea使用入门","url":"video/base28.mp4"},{"preview":"video/thumb/base29.jpg","title":"面向对象入门","url":"video/base29.mp4"},{"preview":"video/thumb/base30.jpg","title":"静态属性和动态行为","url":"video/base30.mp4"},{"preview":"video/thumb/base31.jpg","title":"面向对象","url":"video/base31.mp4"},{"preview":"video/thumb/base32.jpg","title":"面向对象实战-洗衣机","url":"video/base32.mp4"},{"preview":"video/thumb/base33.jpg","title":"面向对象实战-洗衣机升级","url":"video/base33.mp4"},{"preview":"video/thumb/base34.jpg","title":"面向对象实战-封装","url":"video/base34.mp4"},{"preview":"video/thumb/base35.jpg","title":"面向对象-继承(open和override)","url":"video/base35.mp4"},{"preview":"video/thumb/base36.jpg","title":"抽象类和继承","url":"video/base36.mp4"},{"preview":"video/thumb/base37.jpg","title":"面向对象-多态","url":"video/base37.mp4"},{"preview":"video/thumb/base38.jpg","title":"面向对象-抽象类和接口","url":"video/base38.mp4"},{"preview":"video/thumb/base39.jpg","title":"面向对象-代理和委托","url":"video/base39.mp4"},{"preview":"video/thumb/base40.jpg","title":"面向对象-单利模式","url":"video/base40.mp4"},{"preview":"video/thumb/base41.jpg","title":"面向对象-枚举","url":"video/base41.mp4"},{"preview":"video/thumb/base42.jpg","title":"面向对象-印章类","url":"video/base42.mp4"}]
     */

    private String msg;
    private String code;
    private List<BaseVideo> detail;

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

    public List<BaseVideo> getDetail() {
        return detail;
    }

    public void setDetail(List<BaseVideo> detail) {
        this.detail = detail;
    }

    public static class BaseVideo {
        /**
         * preview : video/thumb/base04.jpg
         * title : 你好世界
         * url : video/base04.mp4
         */

        private String thumb;
        private String title;
        private String url;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String preview) {
            this.thumb = preview;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
