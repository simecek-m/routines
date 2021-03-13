package dev.simecek.routines.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import dev.simecek.routines.R
import dev.simecek.routines.constant.Icon
import dev.simecek.routines.databinding.ViewIconPickerBinding
import dev.simecek.routines.listener.IconPickerListener

class IconPickerView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private lateinit var binding: ViewIconPickerBinding
    private var pickedIconOrdinal: Int = 0
    var listener: IconPickerListener? = null

    fun getSelectedIcon():Icon {
        return Icon.values()[pickedIconOrdinal]
    }

    fun setSelectedIcon(icon: Icon) {
        requestFocusAndHideKeyboard()
        refreshUI(pickedIconOrdinal, icon.ordinal)
        pickedIconOrdinal = icon.ordinal
        listener?.onSelectedIconChanged()
    }

    init {
        if(isInEditMode) {
            View.inflate(context, R.layout.view_icon_picker, this)
        } else {
            binding = ViewIconPickerBinding.inflate(LayoutInflater.from(context), this, true)
            binding.view = this
            val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.IconPickerView, 0, 0)
            pickedIconOrdinal = styledAttributes.getInteger(R.styleable.IconPickerView_pickedIcon, 0)
            refreshUI(null, pickedIconOrdinal)
            styledAttributes.recycle()
        }

    }

    private fun refreshUI(oldIconOrdinal: Int?, newIconOrdinal: Int) {
        findIconView(oldIconOrdinal)?.setPicked(false)
        findIconView(newIconOrdinal)?.setPicked(true)
    }

    private fun findIconView(iconOrdinal: Int?): IconView? {
        return when (iconOrdinal) {
            Icon.IC_LOOP.ordinal -> binding.iconLoop
            Icon.IC_SCHOOL.ordinal -> binding.iconSchool
            Icon.IC_WALK.ordinal -> binding.iconWalk
            Icon.IC_WORK.ordinal -> binding.iconWork
            Icon.IC_BOOK.ordinal -> binding.iconBook
            Icon.IC_MEDITATION.ordinal -> binding.iconMeditation
            Icon.IC_SPORT.ordinal -> binding.iconSport
            Icon.IC_FOOD.ordinal -> binding.iconFood
            Icon.IC_GYM.ordinal -> binding.iconGym
            Icon.IC_BATH.ordinal -> binding.iconBath
            Icon.IC_TOOTH_BRUSH.ordinal -> binding.iconToothBrush
            Icon.IC_PET.ordinal -> binding.iconPet
            Icon.IC_COMPASS.ordinal -> binding.iconCompass
            Icon.IC_BEER.ordinal -> binding.iconBeer
            Icon.IC_CHAT.ordinal -> binding.iconChat
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
            if(view.pickedIconOrdinal != icon.ordinal) {
                view.setSelectedIcon(icon)
            }
        }
    }

    private fun requestFocusAndHideKeyboard() {
        requestFocus()
        val manager: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
        manager?.hideSoftInputFromWindow(windowToken, 0)
    }

}