package dev.simecek.routines.handler

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData

class IconUiHandler(val icon: Drawable, val selectedIcon: Drawable, var picked: MutableLiveData<Boolean>)