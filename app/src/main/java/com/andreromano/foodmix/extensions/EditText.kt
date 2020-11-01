package com.andreromano.foodmix.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.StringRes
import com.andreromano.foodmix.R


fun EditText.setTextChangedListener(action: ((text: Editable) -> Unit)?) {
    val currentWatcher: TextWatcher? = if (action != null) {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                action(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        }
    } else {
        null
    }

    if (currentWatcher == null) {
        clearTextChangedListener()
        return
    }
    if (this.watcher != null) clearTextChangedListener()

    addTextChangedListener(currentWatcher)
    this.watcher = currentWatcher
}

private fun EditText.clearTextChangedListener() {
    watcher?.let {
        removeTextChangedListener(it)
    }
    watcher = null
}

private var EditText.watcher: TextWatcher?
    get() = getTag(R.id.tag_edittext_watchers) as? TextWatcher
    set(value) = setTag(R.id.tag_edittext_watchers, value)

fun EditText.setTextWithoutWatcher(text: CharSequence?) {
    val currentWatcher = watcher
    currentWatcher?.let { removeTextChangedListener(currentWatcher) }
    this.setText(text)
    currentWatcher?.let { addTextChangedListener(currentWatcher) }
}

fun EditText.setTextWithoutWatcher(@StringRes resid: Int) {
    val currentWatcher = watcher
    currentWatcher?.let { removeTextChangedListener(currentWatcher) }
    this.setText(resid)
    currentWatcher?.let { addTextChangedListener(currentWatcher) }
}
