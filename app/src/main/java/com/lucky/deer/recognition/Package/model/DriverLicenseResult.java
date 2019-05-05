package com.lucky.deer.recognition.Package.model;

/**
 * 驾驶证解析类
 */
public class DriverLicenseResult {


    private long log_id;
    private String direction;
    private String words_result_num;
    private WordsResultBean words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(String words_result_num) {
        this.words_result_num = words_result_num;
    }

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * 证号
         */
        private CommonBean 证号;
        /**
         * 有效期限
         */
        private CommonBean 有效期限;
        /**
         * 准驾车型
         */
        private CommonBean 准驾车型;
        /**
         * 证件有效期
         */
        private CommonBean 至;
        /**
         * 有效起始日期
         */
        private CommonBean 有效起始日期;
        /**
         * 住址
         */
        private CommonBean 住址;
        /**
         * 姓名
         */
        private CommonBean 姓名;
        /**
         * 国籍
         */
        private CommonBean 国籍;
        /**
         * 出生日期
         */
        private CommonBean 出生日期;
        /**
         * 性别
         */
        private CommonBean 性别;
        /**
         * 初次领证日期
         */
        private CommonBean 初次领证日期;

        public CommonBean getCertificateNumber() {
            return 证号;
        }

        public void setCertificateNumber(CommonBean 证号) {
            this.证号 = 证号;
        }

        public CommonBean getValidityPeriod() {
            return 有效期限;
        }

        public void setValidityPeriod(CommonBean 有效期限) {
            this.有效期限 = 有效期限;
        }

        public CommonBean getDrivingModel() {
            return 准驾车型;
        }

        public void setDrivingModel(CommonBean 准驾车型) {
            this.准驾车型 = 准驾车型;
        }

        public CommonBean getUntil() {
            return 至;
        }

        public void setUntil(CommonBean 至) {
            this.至 = 至;
        }

        public CommonBean getEffectiveStartDate() {
            return 有效起始日期;
        }

        public void setEffectiveStartDate(CommonBean 有效起始日期) {
            this.有效起始日期 = 有效起始日期;
        }

        public CommonBean getAddress() {
            return 住址;
        }

        public void setAddress(CommonBean 住址) {
            this.住址 = 住址;
        }

        public CommonBean getName() {
            return 姓名;
        }

        public void setName(CommonBean 姓名) {
            this.姓名 = 姓名;
        }

        public CommonBean getCountryCitizenship() {
            return 国籍;
        }

        public void setCountryCitizenship(CommonBean 国籍) {
            this.国籍 = 国籍;
        }

        public CommonBean getDateBirth() {
            return 出生日期;
        }

        public void setDateBirth(CommonBean 出生日期) {
            this.出生日期 = 出生日期;
        }

        public CommonBean getSex() {
            return 性别;
        }

        public void setSex(CommonBean 性别) {
            this.性别 = 性别;
        }

        public CommonBean getInitialLicenseDate() {
            return 初次领证日期;
        }

        public void setInitialLicenseDate(CommonBean 初次领证日期) {
            this.初次领证日期 = 初次领证日期;
        }

        public static class CommonBean {
            /**
             * 数据
             */
            private String words;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }
        }
    }
}
