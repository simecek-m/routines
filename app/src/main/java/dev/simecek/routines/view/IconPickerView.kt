package dev.simecek.routines.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.airbnb.paris.extensions.style
import dev.simecek.routines.R
import dev.simecek.routines.constant.Icon
import dev.simecek.routines.databinding.ViewIconPickerBinding
import dev.simecek.routines.listener.IconPickerListener

class IconPickerView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private lateinit var binding: ViewIconPickerBinding
    private var selectedIcon: Int = 0
    var listener: IconPickerListener? = null

    fun getSelectedIcon():Icon {
        return Icon.values()[selectedIcon]
    }

    fun setSelectedIcon(icon: Icon) {
        refreshUI(selectedIcon, icon.ordinal)
        selectedIcon = icon.ordinal
        listener?.onSelectedIconChanged()
    }

    init {
        if(isInEditMode) {
            View.inflate(context, R.layout.view_icon_picker, this)
        } else {
            binding = ViewIconPickerBinding.inflate(LayoutInflater.from(context),this, true)
            binding.view = this
            val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.IconPickerView,0, 0)
            selectedIcon = styledAttributes.getInteger(R.styleable.IconPickerView_selectedIcon, 0)
            refreshUI(null, selectedIcon)
            styledAttributes.recycle()
        }

    }

    private fun refreshUI(oldIcon: Int?, newIcon: Int) {
        findIconView(oldIcon)?.style(R.style.Icon)
        findIconView(newIcon)?.style(R.style.Icon_Selected)
    }

    private fun findIconView(iconTypeNumber: Int?): ImageView? {
        return when (iconTypeNumber) {
            0 -> binding.iconLoop
            1 -> binding.iconSchool
            2 -> binding.iconWalk
            3 -> binding.iconWork
            4 -> binding.iconBook
            5 -> binding.iconMeditation
            6 -> binding.iconSport
            7 -> binding.iconFood
            8 -> binding.iconGym
            9 -> binding.iconBath
            10 -> binding.iconToothBrush
            11 -> binding.iconPet
            12 -> binding.iconCompass
            13 -> binding.iconBeer
            14 -> binding.iconChat
            else -> null
        }
    }

    companion object {
        @BindingAdapter("app:selectedIconAttrChanged")
        @JvmStatic
        fun setListeners(view: IconPickerView, attrChange: InverseBindingListener) {
            view.listener = object: IconPickerListener {
                override fun onSelectedIconChanged() {
                    attrChange.onChange()
                }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "app:selectedIcon")
        fun getSelectedIcon(view: IconPickerView): Icon {
            return view.getSelectedIcon()
        }

        @JvmStatic
        @BindingAdapter("app:selectedIcon")
        fun setSelectedIcon(view: IconPickerView, icon: Icon) {
            if(view.selectedIcon != icon.ordinal) {
                view.setSelectedIcon(icon)
            }
        }
    }

}