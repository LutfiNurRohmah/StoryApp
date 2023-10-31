package com.lutfi.storyapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MyEmailEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = s.toString()
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

                if (!email.matches(emailPattern.toRegex()) && email.length > 0) {
                    setError("Format email tidak valid", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
