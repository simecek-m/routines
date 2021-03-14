package dev.simecek.routines.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewTitleBinding

class TitleView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        val binding: ViewTitleBinding = ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView,0, 0)
        val text: String = styledAttributes.getString(R.styleable.TitleView_text)?: context.getString(R.string.title)
        val icon: Drawable? = styledAttributes.getDrawable(R.styleable.TitleView_icon)
        val color: Int = styledAttributes.getColor(R.styleable.TitleView_color, ContextCompat.getColor(context, R.color.brand))
        binding.text.text = text
        binding.text.setTextColor(color)
        if(icon == null) {
            binding.icon.visibility = GONE
        } else {
            binding.icon.background = icon
            DrawableCompat.setTint(binding.icon.background, color)
        }
    }
}