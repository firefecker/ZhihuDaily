package com.fire.zhihudaily.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader.TileMode.CLAMP
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.animation.Animation
import android.widget.ImageView

/**
 * Created by fire on 2017/12/8.
 * Date：2017/12/8
 * Author: fire
 * Description:
 */
class CircleImageView : ImageView{

    private val KEY_SHADOW_COLOR = 0x1E000000
    private val FILL_SHADOW_COLOR = 0x3D000000
    // PX
    private val X_OFFSET = 0f
    private val Y_OFFSET = 1.75f
    private val SHADOW_RADIUS = 3.5f
    private val SHADOW_ELEVATION = 0

    private var mListener: Animation.AnimationListener? = null
    internal var mShadowRadius: Int = 0

    constructor(context: Context, color: Int): this(context,null,color)
    constructor(context: Context?, attrs: AttributeSet?, color: Int) : this(context, attrs,0,color)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, color: Int) : super(context, attrs,
            defStyleAttr) {
        val density = getContext().resources.displayMetrics.density
        val shadowYOffset = (density * Y_OFFSET).toInt()
        val shadowXOffset = (density * X_OFFSET).toInt()

        mShadowRadius = (density * SHADOW_RADIUS).toInt()

        val circle: ShapeDrawable
        if (elevationSupported()) {
            circle = ShapeDrawable(OvalShape())
            ViewCompat.setElevation(this, SHADOW_ELEVATION * density)
        } else {
            val oval = OvalShadow(mShadowRadius)
            circle = ShapeDrawable(oval)
            ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.paint)
            circle.paint.setShadowLayer(mShadowRadius.toFloat(), shadowXOffset.toFloat(),
                    shadowYOffset.toFloat(),
                    KEY_SHADOW_COLOR)
            val padding = mShadowRadius
            // set padding so the inner image sits correctly within the shadow.
            setPadding(padding, padding, padding, padding)
        }
        circle.paint.color = color
        ViewCompat.setBackground(this, circle)
    }

    private fun elevationSupported(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= 21
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!elevationSupported()) {
            setMeasuredDimension(measuredWidth + mShadowRadius * 2,
                    measuredHeight + mShadowRadius * 2)
        }
    }

    fun setAnimationListener(listener: Animation.AnimationListener) {
        mListener = listener
    }

    public override fun onAnimationStart() {
        super.onAnimationStart()
        if (mListener != null) {
            mListener!!.onAnimationStart(animation)
        }
    }

    public override fun onAnimationEnd() {
        super.onAnimationEnd()
        if (mListener != null) {
            mListener!!.onAnimationEnd(animation)
        }
    }

    /**
     * Update the background color of the circle image view.
     *
     * @param colorRes Id of a color resource.
     */
    fun setBackgroundColorRes(colorRes: Int) {
        setBackgroundColor(ContextCompat.getColor(context, colorRes))
    }

    override fun setBackgroundColor(color: Int) {
        if (background is ShapeDrawable) {
            (background as ShapeDrawable).paint.color = color
        }
    }

    private inner class OvalShadow internal constructor(shadowRadius: Int) : OvalShape() {
        private var mRadialGradient: RadialGradient? = null
        private val mShadowPaint: Paint

        init {
            mShadowPaint = Paint()
            mShadowRadius = shadowRadius
            updateRadialGradient(rect().width().toInt())
        }

        override fun onResize(width: Float, height: Float) {
            super.onResize(width, height)
            updateRadialGradient(width.toInt())
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            val viewWidth = this@CircleImageView.width
            val viewHeight = this@CircleImageView.height
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(),
                    (viewWidth / 2).toFloat(), mShadowPaint)
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(),
                    (viewWidth / 2 - mShadowRadius).toFloat(), paint)
        }

        private fun updateRadialGradient(diameter: Int) {
            mRadialGradient = RadialGradient((diameter / 2).toFloat(), (diameter / 2).toFloat(),
                    mShadowRadius.toFloat(), intArrayOf(FILL_SHADOW_COLOR, Color.TRANSPARENT),
                    null, CLAMP)
            mShadowPaint.shader = mRadialGradient
        }
    }
}