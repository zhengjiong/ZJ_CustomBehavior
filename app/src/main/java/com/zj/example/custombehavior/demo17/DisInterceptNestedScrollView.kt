package com.zj.example.custombehavior.demo17

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 *
 * CreateTime:2018/9/29  14:36
 * @author 郑炯
 * @version 1.0
 */


class DisInterceptNestedScrollView(context: Context, attrs: AttributeSet? = null, defstyleAttr: Int = 0) : NestedScrollView(context, attrs, defstyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        //requestDisallowInterceptTouchEvent(true)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //告诉父view不拦截我自己的滑动事件
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        /*when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                requestDisallowInterceptTouchEvent(true);
            }
            MotionEvent.ACTION_UP -> {
                requestDisallowInterceptTouchEvent(false);
            }
            MotionEvent.ACTION_CANCEL -> {
                requestDisallowInterceptTouchEvent(false);
            }
        }*/
        return super.onTouchEvent(ev)
    }
}