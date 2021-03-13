package dev.simecek.routines.handler

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData

class IconUiHandler(val iconDefault: Drawable, val iconPicked: Drawable, var pickedState: MutableLiveData<Boolean>)