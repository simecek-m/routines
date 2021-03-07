package dev.simecek.routines.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewTitleBinding

class TitleView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        val binding: ViewTitleBinding = ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView,0, 0)
        val text: String = styledAttributes.getString(R.styleable.TitleView_text)?: context.getString(R.string.title)
        val icon: Drawable? = styledAttributes.getDrawable(R.styleable.TitleView_icon)
        binding.text.text = text
        if(icon == null) {
            binding.icon.visibility = GONE
        } else {
            binding.icon.background = icon
        }
    }
}