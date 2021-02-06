package org.example.demo.create;

/**
 * 监听器接口，可以监听新事件的到来，也能监听事件的停止
 */
public interface MyEventListener {

    void onNewEvent(MyEventSource.MyEvent event);

    void onEventStopped();
}
