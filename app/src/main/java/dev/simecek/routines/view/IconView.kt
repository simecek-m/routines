package dev.simecek.routines.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewIconBinding
import dev.simecek.routines.handler.IconUiHandler

class IconView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs), LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private val pickedState: MutableLiveData<Boolean>
    private val iconDefault: Drawable
    private val iconPicked: Drawable

    init {
        val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.IconView,0, 0)
        iconDefault = styledAttributes.getDrawable(R.styleable.IconView_icon) ?: ContextCompat.getDrawable(context, R.drawable.ic_loop)!!
        iconPicked = styledAttributes.getDrawable(R.styleable.IconView_icon) ?: ContextCompat.getDrawable(context, R.drawable.ic_loop)!!
        val pickedAttribute = styledAttributes.getBoolean(R.styleable.IconView_picked, false)
        pickedState = MutableLiveData(pickedAttribute)
        styledAttributes.recycle()
        if(isInEditMode) {
            inflateInEditMode()
        } else {
            inflate()
        }
    }

    fun isPicked(): Boolean {
        return pickedState.value!!
    }

    fun setPicked(picked: Boolean) {
        this.pickedState.value = picked
    }

    private fun inflateInEditMode() {
        val layout = LayoutInflater.from(context).inflate(R.layout.view_icon, this, false)
        val iconDefaultView = layout.findViewById<ImageView>(R.id.icon_default)
        val iconPickedView = layout.findViewById<ImageView>(R.id.icon_picked)
        if(pickedState.value!!) {
            iconDefaultView.visibility = GONE
            iconPickedView.visibility = VISIBLE
            iconPickedView.setImageDrawable(iconPicked)
        } else {
            iconDefaultView.visibility = VISIBLE
            iconPickedView.visibility = GONE
            iconDefaultView.setImageDrawable(iconDefault)
        }
        addView(layout)
    }

    private fun inflate() {
        val binding: ViewIconBinding = ViewIconBinding.inflate(LayoutInflater.from(context), this, true)
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        binding.handler = IconUiHandler(iconDefault, iconPicked, pickedState)
        binding.lifecycleOwner = this
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

}