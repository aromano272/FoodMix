package com.andreromano.foodmix.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView

class SquareImageView : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val constantMeasure = if (layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            measuredHeight
        } else if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            measuredWidth
        } else {
            throw UnsupportedOperationException("You need to set width or height to match_parent")
        }
        setMeasuredDimension(constantMeasure, constantMeasure)
    }
}