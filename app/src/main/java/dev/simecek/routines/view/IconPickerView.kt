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
import dev.simecek.routines.databinding.ViewIconPickerBinding
import dev.simecek.routines.listener.IconPickerListener
import dev.simecek.routines.utils.constant.Icon

class IconPickerView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private lateinit var binding: ViewIconPickerBinding
    private var pickedIconOrdinal: Int = 0
    var listener: IconPickerListener? = null

    fun getPickedIcon():Icon {
        return Icon.values()[pickedIconOrdinal]
    }

    fun setPickedIcon(icon: Icon) {
        requestFocusAndHideKeyboard()
        refreshUI(pickedIconOrdinal, icon.ordinal)
        pickedIconOrdinal = icon.ordinal
        listener?.onPickedIconChanged()
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
            Icon.FOOD.ordinal -> binding.iconFood
            Icon.PIZZA.ordinal -> binding.iconPizza
            Icon.SNACK.ordinal -> binding.iconSnack
            Icon.COFFEE.ordinal -> binding.iconCoffee
            Icon.TEA.ordinal -> binding.iconTea
            Icon.FOOTBALL.ordinal -> binding.iconFootball
            Icon.BASKETBALL.ordinal -> binding.iconBasketball
            Icon.VOLLEYBALL.ordinal -> binding.iconVolleyball
            Icon.TENNIS.ordinal -> binding.iconTennis
            Icon.GOLF.ordinal -> binding.iconGolf
            Icon.GYM.ordinal -> binding.iconGym
            Icon.RUNNING.ordinal -> binding.iconRunning
            Icon.CYCLING.ordinal -> binding.iconCycling
            Icon.HIKING.ordinal -> binding.iconHiking
            Icon.SWIMMING.ordinal -> binding.iconSwimming
            Icon.MUSIC.ordinal -> binding.iconMusic
            Icon.PAINTING.ordinal -> binding.iconPainting
            Icon.GAMING.ordinal -> binding.iconGaming
            Icon.LAPTOP.ordinal -> binding.iconLaptop
            Icon.DESIGN.ordinal -> binding.iconDesign
            Icon.STUDY.ordinal -> binding.iconStudy
            Icon.WORK.ordinal -> binding.iconWork
            Icon.SLEEP.ordinal -> binding.iconSleep
            Icon.SHOWER.ordinal -> binding.iconShower
            Icon.TOOTHBRUSH.ordinal -> binding.iconToothbrush
            else -> null
        }
    }

    companion object {
        @BindingAdapter("app:selectedIconAttrChanged")
        @JvmStatic
        fun setListeners(view: IconPickerView, attrChange: InverseBindingListener) {
            view.listener = object: IconPickerListener {
                override fun onPickedIconChanged() {
                    attrChange.onChange()
                }
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "app:selectedIcon")
        fun getSelectedIcon(view: IconPickerView): Icon {
            return view.getPickedIcon()
        }

        @JvmStatic
        @BindingAdapter("app:selectedIcon")
        fun setSelectedIcon(view: IconPickerView, icon: Icon) {
            if(view.pickedIconOrdinal != icon.ordinal) {
                view.setPickedIcon(icon)
            }
        }
    }

    private fun requestFocusAndHideKeyboard() {
        requestFocus()
        val manager: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
        manager?.hideSoftInputFromWindow(windowToken, 0)
    }

}