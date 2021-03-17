package dev.simecek.routines.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewTitleBinding
import dev.simecek.routines.utils.constant.TitleSize
import dev.simecek.routines.utils.theme.ColorUtils

class TitleView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private val binding: ViewTitleBinding = ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)
    private val text: String
    private val icon: Drawable?
    private val size: Int
    private val color: Int
    private val colorPrimary: Int = ColorUtils.getPrimaryColor(context)

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0)
        text = styledAttributes.getString(R.styleable.TitleView_text)?: context.getString(R.string.title)
        icon = styledAttributes.getDrawable(R.styleable.TitleView_icon)
        size = styledAttributes.getInt(R.styleable.TitleView_size, TitleSize.MEDIUM.ordinal)
        color = styledAttributes.getColor(R.styleable.TitleView_color, colorPrimary)
        styledAttributes.recycle()
        setSize()
        setContentAndColor()
    }

    private fun setContentAndColor() {
        binding.text.text = text
        binding.text.setTextColor(color)
        if(icon == null) {
            binding.icon.visibility = GONE
        } else {
            binding.icon.visibility = VISIBLE
            binding.icon.background = icon
            DrawableCompat.setTint(binding.icon.background, color)
        }
    }

    private fun setSize() {
        println(size)
        when(size) {
            TitleSize.SMALL.ordinal -> {
                TextViewCompat.setTextAppearance(binding.text, R.style.TextAppearance_MdcTypographyStyles_Subtitle1)
            }
            TitleSize.MEDIUM.ordinal -> {
                TextViewCompat.setTextAppearance(binding.text, R.style.TextAppearance_MdcTypographyStyles_Headline6)
            }
            TitleSize.BIG.ordinal -> {
                TextViewCompat.setTextAppearance(binding.text, R.style.TextAppearance_MdcTypographyStyles_Headline4)
            }
        }
    }

}