package com.th3pl4gu3.mes.ui.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.utils.extensions.loadImageUrl
import com.th3pl4gu3.mes.ui.utils.extensions.requireStringRes

/*
* Loads a given url image to an ImageView
*/
@BindingAdapter("configureLogo")
fun ImageView.configureLogo(url: String) = loadImageUrl(
    url.toUri().buildUpon()?.scheme("https")?.build()
)


/*
* Formats a given MUR phone number
*/
@BindingAdapter("toMurPhoneNumberStringFormat")
fun TextView.toMurPhoneNumberStringFormat(number: Long) {
    this.text = number.toMruPhoneNumberString()
}

/*
* Loads a given icon
*/
@BindingAdapter("loadIcon")
fun ImageView.loadIcon(icon: Int) {
    this.setImageResource(icon)
}

/*
* Verify if a text field is empty when required
* is set to true
* If it's empty and it's required, error message will be displayed
* Can be added to any TextInputLayout where data cannot be empty or null
*/
@BindingAdapter("isRequired")
fun TextInputLayout.isRequired(required: Boolean) {
    if (required) {
        val textField = this.editText
        if (textField?.text.isNullOrEmpty()) {
            this.error = this.requireStringRes(R.string.message_error_field_empty)
        } else {
            this.error = null
        }
    }
}