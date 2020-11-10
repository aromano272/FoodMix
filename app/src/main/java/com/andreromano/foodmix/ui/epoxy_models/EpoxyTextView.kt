package com.andreromano.foodmix.ui.epoxy_models

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EpoxyTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @TextProp
    fun setTitle(title: CharSequence) {
        text = title
    }

    @CallbackProp
    fun setClickListener(clickListener: View.OnClickListener?) {
        setOnClickListener(clickListener)
    }
}