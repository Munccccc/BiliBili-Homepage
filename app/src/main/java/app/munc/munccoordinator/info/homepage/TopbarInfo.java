package app.munc.munccoordinator.info.homepage;

import java.util.List;

/**
 * Created by GD on 2017/12/19.
 */

public class TopbarInfo {

    /**
     * code : 0
     * data : [{"id":10,"rank":20,"logo":"http://i0.hdslb.com/bfs/archive/b81c8db65adaf8bab17960fb731a553e7ddf6f8d.png","logo_white":"http://i0.hdslb.com/bfs/archive/74ddfab2673f277b97b58adccdd0f3b96665b1e3.png","name":"会员购","param":"bilibili://mall/home?from=homepage","module":4,"online_time":0}]
     * message : 成功
     * ttl : 1
     */

    private int code;
    private String message;
    private int ttl;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 10
         * rank : 20
         * logo : http://i0.hdslb.com/bfs/archive/b81c8db65adaf8bab17960fb731a553e7ddf6f8d.png
         * logo_white : http://i0.hdslb.com/bfs/archive/74ddfab2673f277b97b58adccdd0f3b96665b1e3.png
         * name : 会员购
         * param : bilibili://mall/home?from=homepage
         * module : 4
         * online_time : 0
         */

        private int id;
        private int rank;
        private String logo;
        private String logo_white;
        private String name;
        private String param;
        private int module;
        private int online_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo_white() {
            return logo_white;
        }

        public void setLogo_white(String logo_white) {
            this.logo_white = logo_white;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public int getModule() {
            return module;
        }

        public void setModule(int module) {
            this.module = module;
        }

        public int getOnline_time() {
            return online_time;
        }

        public void setOnline_time(int online_time) {
            this.online_time = online_time;
        }
    }
}
