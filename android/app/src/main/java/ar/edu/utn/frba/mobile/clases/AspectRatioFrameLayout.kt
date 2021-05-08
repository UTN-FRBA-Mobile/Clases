package ar.edu.utn.frba.mobile.clases

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class AspectRatioFrameLayout : FrameLayout {

    private var widthHeightRatio = 1f
    private var fixedEdge = UNDEFINED

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout)
        widthHeightRatio = a.getFloat(R.styleable.AspectRatioFrameLayout_widthHeightRatio, widthHeightRatio)
        fixedEdge = a.getInt(R.styleable.AspectRatioFrameLayout_fixedEdge, fixedEdge)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val fixedSizeIsWidth: Boolean
        when (fixedEdge) {
            FIXED_WIDTH -> fixedSizeIsWidth = true
            FIXED_HEIGHT -> fixedSizeIsWidth = false
            UNDEFINED -> fixedSizeIsWidth = width > 0 && width < widthHeightRatio * height || height == 0
            else -> fixedSizeIsWidth = width > 0 && width < widthHeightRatio * height || height == 0
        }
        width = if (fixedSizeIsWidth) width else (widthHeightRatio * height).toInt()
        height = if (fixedSizeIsWidth) (width.toFloat() / widthHeightRatio).toInt() else height
        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec)
    }

    companion object {

        val UNDEFINED = 0
        val FIXED_WIDTH = -1
        val FIXED_HEIGHT = 1
    }
}
