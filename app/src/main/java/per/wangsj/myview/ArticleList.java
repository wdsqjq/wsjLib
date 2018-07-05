package per.wangsj.myview;

import java.util.List;

/**
 * Created by shiju.wang on 2018/6/21.
 *
 * @author shiju.wang
 */
public class ArticleList {

    /**
     * course : [{"id":19,"read":1,"title":"基本语法","flag":"准备开始","url":"static/kotlin_article/1_1.html"},{"id":20,"read":1,"title":"习惯用语","flag":"准备开始","url":"static/kotlin_article/1_2.html"},{"id":21,"read":1,"title":"编码风格","flag":"准备开始","url":"static/kotlin_article/1_3.html"}]
     * flag : 准备开始
     */

    private String flag;
    private List<CourseBean> course;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<CourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<CourseBean> course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "flag='" + flag + '\'' +
                ", course=" + course +
                '}';
    }

    public static class CourseBean {
        /**
         * id : 19
         * read : 1
         * title : 基本语法
         * flag : 准备开始
         * url : static/kotlin_article/1_1.html
         */

        private int id;
        private int read;
        private String title;
        private String flag;
        private String url;

        @Override
        public String toString() {
            return "CourseBean{" +
                    "id=" + id +
                    ", read=" + read +
                    ", title='" + title + '\'' +
                    ", flag='" + flag + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
