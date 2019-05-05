package com.lucky.model.common.push;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 通知信息
 *
 * @author wangxiangyi
 * @date 2018/11/21
 */
public class NotificationBean implements Serializable {

    /**
     * ios : {"alert":"我的通知消息","extras":{"schema":""},"badge":"+1","sound":"sound.caf","content-available":1}
     * android : {"alert":"我的通知消息","extras":{"sss":""},"title":"您有新办消息，请注意查收！"}
     */

    private IosBean ios;
    private AndroidBean android;

    public IosBean getIos() {
        return ios;
    }

    public void setIos(IosBean ios) {
        this.ios = ios;
    }

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public static class IosBean {
        /**
         * alert : 我的通知消息
         * extras : {"schema":""}
         * badge : +1
         * sound : sound.caf
         * content-available : 1
         */

        private String alert;
        private IosBean.ExtrasBean extras;
        private String badge;
        private String sound;
        @SerializedName("content-available")
        private int contentavailable;

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public IosBean.ExtrasBean getExtras() {
            return extras;
        }

        public void setExtras(IosBean.ExtrasBean extras) {
            this.extras = extras;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public int getContentavailable() {
            return contentavailable;
        }

        public void setContentavailable(int contentavailable) {
            this.contentavailable = contentavailable;
        }

        public static class ExtrasBean {
            /**
             * schema :
             */

            private String schema;

            public String getSchema() {
                return schema;
            }

            public void setSchema(String schema) {
                this.schema = schema;
            }
        }
    }

    public static class AndroidBean {
        /**
         * alert : 我的通知消息
         * extras : {"sss":""}
         * title : 您有新办消息，请注意查收！
         */

        private String alert;
        private AndroidBean.ExtrasBeanX extras;
        private String title;

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public AndroidBean.ExtrasBeanX getExtras() {
            return extras;
        }

        public void setExtras(AndroidBean.ExtrasBeanX extras) {
            this.extras = extras;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class ExtrasBeanX {
            /**
             * sss :
             */

            private String sss;

            public String getSss() {
                return sss;
            }

            public void setSss(String sss) {
                this.sss = sss;
            }
        }
    }
}
