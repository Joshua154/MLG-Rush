package com.laudynetwork.mlgrush.timer;

public enum TimerMode {
    COUNTUP {
        @Override
        public int toInt() {
            return 1;
        }
    },
    COUNTDOWN {
        @Override
        public int toInt() {
            return -1;
        }
    };

    public int toInt() {
        return 0;
    }
}
