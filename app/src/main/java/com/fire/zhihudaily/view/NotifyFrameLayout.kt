package com.fire.zhihudaily.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class NotifyFrameLayout : FrameLayout{

    private val CIRCLE_BG_LIGHT = -0x50506
    private var mCircleViewBottom: CircleImageView? = null
    private var mProgressBottom: MaterialProgressDrawable? = null


    private var valueAnimator: ValueAnimator? = null
    internal var start = false
    internal var visable = false

    private val colors = intArrayOf(-0x10000, -0x8100, -0x100, -0xff0100, -0xff0001, -0xffff01,
            -0x74ff01)


    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
            defStyleAttr) {
        initView()
    }

    private fun initView() {
        mCircleViewBottom = CircleImageView(context, CIRCLE_BG_LIGHT)
        mProgressBottom = MaterialProgressDrawable(context, mCircleViewBottom!!)
        mCircleViewBottom!!.setVisibility(View.GONE)
        mProgressBottom!!.setBackgroundColor(CIRCLE_BG_LIGHT)
        //圈圈颜色,可以是多种颜色
        mProgressBottom!!.setColorSchemeColors(colors)
        //设置圈圈的各种大小
        mProgressBottom!!.updateSizes(0)
        mCircleViewBottom!!.setImageDrawable(mProgressBottom)
        addView(mCircleViewBottom)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0f, 1f)
            valueAnimator!!.setDuration(600)
            valueAnimator!!.setInterpolator(DecelerateInterpolator())
            valueAnimator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
                val n = animation.animatedValue as Float
                //圈圈的旋转角度
                mProgressBottom!!.setProgressRotation(n * 0.5f)
                //圈圈周长，0f-1F
                mProgressBottom!!.setStartEndTrim(0f, n * 0.8f)
                //箭头大小，0f-1F
                mProgressBottom!!.setArrowScale(n)
                //透明度，0-255
                mProgressBottom!!.setAlpha((255 * n).toInt())
            })
            valueAnimator!!.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    visable = true
                    start()
                }
            })
        }
        if (!valueAnimator!!.isRunning()) {
            if (!visable) {
                //是否显示箭头
                mProgressBottom!!.showArrow(true)
                valueAnimator!!.start()
            }
        }
    }

    fun start() {
        if (visable) {
            if (!start) {
                mProgressBottom!!.start()
                start = true
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator!!.end()
        if (start) {
            mProgressBottom!!.stop()
            start = false
            visable = false
        }
    }
}