package dev.simecek.routines.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import dev.simecek.routines.R
import dev.simecek.routines.databinding.ViewIconBinding
import dev.simecek.routines.handler.IconUiHandler

class IconView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs), LifecycleOwner, LifecycleObserver {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private var picked: MutableLiveData<Boolean> = MutableLiveData(false)
    val binding: ViewIconBinding = ViewIconBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if(isInEditMode) {
            View.inflate(context, R.layout.view_icon, this)
        } else {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
            val styledAttributes = context.theme.obtainStyledAttributes(attrs, R.styleable.IconView,0, 0)
            picked.value = styledAttributes.getBoolean(R.styleable.IconView_picked, false)
            val icon: Drawable = styledAttributes.getDrawable(R.styleable.IconView_icon) ?: ContextCompat.getDrawable(context, R.drawable.ic_loop)!!
            val iconSelected: Drawable = styledAttributes.getDrawable(R.styleable.IconView_icon) ?: ContextCompat.getDrawable(context, R.drawable.ic_loop)!!
            val handler = IconUiHandler(icon, iconSelected, picked)
            binding.handler = handler
            binding.lifecycleOwner = this
        }
    }

    fun isPicked(): Boolean {
        return picked.value!!
    }

    fun setPicked(picked: Boolean) {
        this.picked.value = picked
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

}