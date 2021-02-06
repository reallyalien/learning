package org.example.demo.create;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyEventSource {

    private List<MyEventListener> listeners;

    public MyEventSource() {
        this.listeners = new ArrayList<>();
    }

    /**
     * 注册监听器
     *
     * @param listener
     */
    public void register(MyEventListener listener) {
        listeners.add(listener);
    }

    /**
     * 向监听器发出一个新的事件
     *
     * @param event
     */
    public void newEvent(MyEvent event) {
        for (MyEventListener listener : listeners) {
            listener.onNewEvent(event);
        }
    }

    /**
     * 告诉监听器事件源已经停止
     */
    public void eventStop() {
        for (MyEventListener listener : listeners) {
            listener.onEventStopped();
        }
    }

    public static class MyEvent {
        private Date timeStamp;
        private String message;

        public MyEvent() {
        }

        public MyEvent(Date timeStamp, String message) {
            this.timeStamp = timeStamp;
            this.message = message;
        }

        public Date getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(Date timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "MyEvent{" +
                    "timeStamp=" + timeStamp +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
