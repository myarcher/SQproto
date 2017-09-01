package com.nimi.sqprotos.toast;

/**
 * @author
 * @version 1.0
 * @date 2017/8/3
 */

public enum  Types {
        OK(1000, 0), ERREY(1500, 1), NOTI(1500, 2), GO(0, 3);

        private Types(int time, int indexs) {
            this.indexs = indexs;
            this.time = time;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        private int time;
        private int indexs;

        public int getIndexs() {
            return indexs;
        }

        public void setIndexs(int indexs) {
            this.indexs = indexs;
        }

    
}
