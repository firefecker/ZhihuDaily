package com.fire.zhihudaily.event

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class EventBase {
    private var arg0 : Int
    private var arg1 : Int
    private var arg2 : String
    private var arg3 : String
    private var receiver : Any
    private var sender : Any

    constructor(arg0: Int, arg1: Int, arg2: String, arg3: String, receiver: Any, sender: Any) {
        this.arg0 = arg0
        this.arg1 = arg1
        this.arg2 = arg2
        this.arg3 = arg3
        this.receiver = receiver
        this.sender = sender
    }

    fun arg0(): Int? {
        return arg0
    }

    fun arg1(): Int? {
        return arg1
    }

    fun arg2(): String? {
        return arg2
    }

    fun arg3(): String? {
        return arg3
    }

    fun receiver(): Any? {
        return receiver
    }

    fun sender(): Any? {
        return sender
    }

    object Builder {
        var arg0 : Int = 0
        var arg1 : Int = 0
        var arg2 : String = ""
        var arg3 : String = ""
        var receiver : Any = ""
        var sender : Any = ""
         fun sender(value: Class<out Any>): Builder {
             this.sender = value
             return this
         }

        fun receiver(value: Class<out Any>): Builder {
            this.receiver = value
            return this
        }

        fun arg0(value: Int?): Builder {
            this.arg0 = value!!
            return this
        }

        fun arg1(value: Int?): Builder {
            this.arg1 = value!!
            return this
        }

        fun arg2(value: String): Builder {
            this.arg2 = value
            return this
        }

        fun arg3(value: String): Builder {
            this.arg3 = value
            return this
        }

        fun build(): EventBase {
            return EventBase(arg0, arg1, arg2, arg3, receiver, sender)
        }
    }

}