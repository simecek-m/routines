package dev.simecek.routines.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.airbnb.paris.extensions.style
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewIconPickerBinding
import dev.simecek.routines.handler.IconPickerHandler


class IconPickerView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var binding: ViewIconPickerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_icon_picker,this, true)
    private lateinit var pickedIcon: ImageView

    fun getPickedIcon():String {
        return pickedIcon.tag.toString()
    }

    private val handler: IconPickerHandler = object: IconPickerHandler {
        override fun pickIcon(view: View) {
            pickedIcon.style(R.style.Icon)
            pickedIcon = view as ImageView
            pickedIcon.style(R.style.Icon_Selected)
        }
    }

    init {
        binding.handler = handler
        pickedIcon = binding.iconLoop
    }

}