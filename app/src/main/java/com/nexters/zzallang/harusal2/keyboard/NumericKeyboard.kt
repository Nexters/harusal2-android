package com.nexters.zzallang.harusal2.keyboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.keyboard.listener.KeyboardClickListener
import com.nexters.zzallang.harusal2.keyboard.util.ViewRunnable
import com.nexters.zzallang.harusal2.keyboard.util.suppressSoftKeyboard

class NumericKeyboard : FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(context, attrs, -1)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(context, attrs, defStyleAttr)
    }

    private val removeChar: ViewRunnable = object : ViewRunnable() {
        override fun run() {
            val listener = KeyboardClickListener(field, fieldMaxLength)

            listener.onClick(view)
            handler.postDelayed(this, 50)
        }
    }

    private var fieldId: Int = 0

    /**
     * Reference to the [EditText] which will take user input.
     */
    var field: EditText? = null
        set(value) {
            value?.suppressSoftKeyboard()
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    /**
     * maxLength of the bound [EditText]
     */
    var fieldMaxLength: Int = 0
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    /**
     * Height for all keys.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var keyHeight: Int = 0
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    /**
     * Text size of a key.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var keyTextSize: Float = 0F
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    /**
     * Text color of a key.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var keyTextColor: Int = 0
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    /**
     * Right bottom key on the keyboard. By default it is disabled.
     * If you set a text here, it will be put in the bound [EditText] without any validation.
     * To change default implementation of the click listener, use [keySpecialListener].
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var keySpecialValue: String = ""
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    /**
     * Custom click listener for the right bottom key.
     * You can do a validation here or perform any other actions when user taps on the key.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var keySpecialListener: OnClickListener? = null
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }


    @Suppress("MemberVisibilityCanBePrivate")
    var topMargin: Int = 0
        set(value) {
            field = value
            if (childCount > 0) {
                updateView(getChildAt(0))
            }
        }

    private fun initAttributes(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.NumericKeyboard, defStyleAttr, 0)
        val defaultKeyTextSize = context.resources.getDimensionPixelSize(R.dimen.keyboard_text_size)
        val defaultKeyHeight = context.resources.getDimensionPixelSize(R.dimen.keyboard_row_height)

        fieldId = attributes.getResourceId(R.styleable.NumericKeyboard_field, 0)
        fieldMaxLength = attributes.getInteger(R.styleable.NumericKeyboard_fieldMaxLength, 0)
        topMargin = attributes.getInteger(R.styleable.NumericKeyboard_topMargin, 0)

        keyHeight = attributes.getDimensionPixelSize(R.styleable.NumericKeyboard_keyHeight, defaultKeyHeight)
        keyTextSize = attributes.getDimensionPixelSize(R.styleable.NumericKeyboard_keyTextSize, defaultKeyTextSize).toFloat()
        keyTextColor = attributes.getColor(R.styleable.NumericKeyboard_keyTextColor, Color.BLACK)

        keySpecialValue = attributes.getString(R.styleable.NumericKeyboard_keySpecial) ?: ""

        post { initViews() }
    }

    private fun initViews() {
        if (field == null) {
            field = rootView.findViewById(fieldId)
        }

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.widget_keyboard, this, false)

        updateView(layout)
        addView(layout)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun updateView(layout: View) {
        val row1 = layout.findViewById<View>(R.id.keyboard_row1)
        val row2 = layout.findViewById<View>(R.id.keyboard_row2)
        val row3 = layout.findViewById<View>(R.id.keyboard_row3)
        val row4 = layout.findViewById<View>(R.id.keyboard_row4)

        val key1 = layout.findViewById<TextView>(R.id.keyboard_key1)
        val key2 = layout.findViewById<TextView>(R.id.keyboard_key2)
        val key3 = layout.findViewById<TextView>(R.id.keyboard_key3)
        val key4 = layout.findViewById<TextView>(R.id.keyboard_key4)
        val key5 = layout.findViewById<TextView>(R.id.keyboard_key5)
        val key6 = layout.findViewById<TextView>(R.id.keyboard_key6)
        val key7 = layout.findViewById<TextView>(R.id.keyboard_key7)
        val key8 = layout.findViewById<TextView>(R.id.keyboard_key8)
        val key9 = layout.findViewById<TextView>(R.id.keyboard_key9)
        val key0 = layout.findViewById<TextView>(R.id.keyboard_key0)
        val key00 = layout.findViewById<TextView>(R.id.keyboard_key00)
        val keyRemove = layout.findViewById<TextView>(R.id.keyboard_key_remove)

        val listener = KeyboardClickListener(field, fieldMaxLength)

        row1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, keyHeight)
        val param = row1.layoutParams as MarginLayoutParams
        param.setMargins(0,topMargin,0,0)
        row1.layoutParams = param

        row2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, keyHeight)
        row3.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, keyHeight)
        row4.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, keyHeight)

        key1.setTextColor(keyTextColor)
        key2.setTextColor(keyTextColor)
        key3.setTextColor(keyTextColor)
        key4.setTextColor(keyTextColor)
        key5.setTextColor(keyTextColor)
        key6.setTextColor(keyTextColor)
        key7.setTextColor(keyTextColor)
        key8.setTextColor(keyTextColor)
        key9.setTextColor(keyTextColor)
        key0.setTextColor(keyTextColor)
        key00.setTextColor(keyTextColor)
        keyRemove.setTextColor(keyTextColor)

        key1.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key2.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key3.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key4.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key5.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key6.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key7.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key8.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key9.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key0.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)
        key00.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyTextSize)

        keyRemove.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.8F * keyTextSize)

        key1.setOnClickListener(listener)
        key2.setOnClickListener(listener)
        key3.setOnClickListener(listener)
        key4.setOnClickListener(listener)
        key5.setOnClickListener(listener)
        key6.setOnClickListener(listener)
        key7.setOnClickListener(listener)
        key8.setOnClickListener(listener)
        key9.setOnClickListener(listener)
        key0.setOnClickListener(listener)
        key00.setOnClickListener(listener)

        keyRemove.setOnClickListener(listener)
        keyRemove.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> handler.postDelayed(removeChar.also { it.view = view }, 750)
                MotionEvent.ACTION_UP -> handler.removeCallbacks(removeChar)
            }

            false
        }
    }
}