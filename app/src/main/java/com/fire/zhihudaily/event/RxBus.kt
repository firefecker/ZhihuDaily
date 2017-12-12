package com.fire.zhihudaily.event

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
@SuppressWarnings("unchecked")
class RxBus {

    private var bus: Relay<Any>

    constructor() {
        bus = PublishRelay.create<Any>().toSerialized()
    }

    companion object {
        fun getDefault():RxBus {
            return Inner.mHttpClient
        }
    }

    private object Inner {
        val mHttpClient = RxBus()

        fun  switchEventBase(receiver: Class<out Any>,sender: Class<out Any>) : ObservableTransformer<EventBase, EventBase> {
            return ObservableTransformer<EventBase,EventBase>(function = {
                return@ObservableTransformer it.filter { eventBase ->
                    var flag = true
                    if (receiver != null) {
                        flag = flag && (receiver == eventBase.receiver() || receiver == eventBase.receiver())
                    }
                    if (sender != null) {
                        flag = flag && (sender == eventBase.sender() || sender == eventBase.sender())
                    }
                    flag
                };
            })
        }
    }

    // 发送一个新的事件
    fun post(o: Any) {
        bus.accept(o)
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }

    /**
     * Helpers
     */
    fun postEventBase(eventBase: EventBase) {
        post(eventBase)
    }

    fun switchToEventBase(receiver: Class<out Any>, sender: Class<out Any>): Observable<EventBase>  {
        return bus.ofType(EventBase::class.java).compose(Inner.switchEventBase(sender,receiver))
    }

}

