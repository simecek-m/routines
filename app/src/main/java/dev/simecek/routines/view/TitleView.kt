package dev.simecek.routines.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewTitleBinding

class TitleView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var binding: ViewTitleBinding = ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView,0, 0)
        binding.text.text = styledAttributes.getString(R.styleable.TitleView_text)?: context.getString(R.string.title)
        binding.icon.background = styledAttributes.getDrawable(R.styleable.TitleView_icon)?: ContextCompat.getDrawable(context, R.drawable.ic_icon)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("app:text")
        fun setText(view: TitleView, text: String) {
            view.binding.text.text = text
        }

        @JvmStatic
        @BindingAdapter("app:icon")
        fun setIcon(view: TitleView, icon: Drawable?) {
            view.binding.icon.background = icon
        }

    }
}