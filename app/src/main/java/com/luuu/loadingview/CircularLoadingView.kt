package com.luuu.loadingview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator


/**
 * Created by lls on 2017/11/13.
 */
class CircularLoadingView : View {

    private var circlePaint = Paint()
    private var animator : ValueAnimator? = null
    private var circleRadius = 0
    private var mWidth = 0
    private var count = 0
    private var colorCount = 0
    private val colorArr = arrayOf(0xFF58B4AB.toInt(), 0xFFF8937E.toInt(), 0xFF717A84.toInt())

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        circlePaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mWidth = if (measuredWidth > height) measuredHeight else measuredWidth
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        circlePaint.color = colorArr[colorCount]
        canvas?.drawCircle(mWidth / 2f, mWidth / 2f,
                 if (isEven(count)) circleRadius.toFloat() else mWidth/2f, circlePaint)
        circlePaint.color = 0xFFFFFFFF.toInt()
        canvas?.drawCircle(mWidth / 2f, mWidth / 2f,
                    if (isEven(count)) 0f else circleRadius.toFloat(), circlePaint)
    }

    private fun isEven(i: Int): Boolean {

        return i and 0x1 == 0
    }

    private fun circularAnimator() {
        animator = ValueAnimator.ofInt(1, mWidth / 2)
        animator?.duration = 800
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.repeatMode = ValueAnimator.RESTART
        animator?.interpolator = DecelerateInterpolator()
        animator?.addUpdateListener {
            circleRadius = (it.animatedValue as Int)
            invalidate()
        }
        animator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator) {
                super.onAnimationRepeat(animation)
                count++
                if (isEven(count)) colorCount++
                if (colorCount == colorArr.size) colorCount = 0
            }
        })
    }


    fun startAnimator() {
        circularAnimator()
        animator?.start()
    }

    fun stopAnimator() {
        if (animator != null) animator?.cancel()
    }
}