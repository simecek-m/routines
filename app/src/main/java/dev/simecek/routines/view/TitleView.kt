package dev.simecek.routines.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewTitleBinding

class TitleView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var binding: ViewTitleBinding = ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView,0, 0)
        binding.text.text = styledAttributes.getString(R.styleable.TitleView_text)?: context.getString(R.string.title)
        binding.icon.background = styledAttributes.getDrawable(R.styleable.TitleView_icon)?: ContextCompat.getDrawable(context, R.drawable.ic_icon)
    }
}