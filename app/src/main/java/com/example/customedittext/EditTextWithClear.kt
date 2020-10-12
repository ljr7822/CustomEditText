package com.example.customedittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import androidx.core.content.ContextCompat

/**
 * 自定义的EditText
 * @author iwen大大怪
 * Create to 2020/10/12 21:17
 * @JvmOverloads：函数重载
 */
class EditTextWithClear @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {
    // 获得清除图标
    private var iconDrawable : Drawable? = null

    // 获取xml自定义属性
    init {
      context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, 0, 0)
          .apply {
              try {
                  val iconId = getResourceId(R.styleable.EditTextWithClear_clearIcon, 0)
                  if (iconId != 0){
                      iconDrawable = ContextCompat.getDrawable(context,iconId)
                  }
              } finally {
                  recycle()
              }
          }
    }
    // 当文字改变时出现图标
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()

    }

    // 重写触摸监听
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            iconDrawable?.let {
                if (e.action == MotionEvent.ACTION_UP
                    && e.x > width - it.intrinsicWidth - 20
                    && e.x < width + 20
                    && e.y > height / 2 - it.intrinsicHeight / 2 - 20
                    && e.y < height / 2 + it.intrinsicHeight / 2 + 20
                ) {
                    text?.clear()
                }
            }
        }
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        toggleClearIcon()
    }

    // 显示图标函数
    private fun toggleClearIcon() {
        val icon = if (isFocused && text?.isNotEmpty() == true) iconDrawable else null
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
    }
}