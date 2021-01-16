package dev.simecek.routines.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.simecek.routines.R
import dev.simecek.routines.constant.IconPickerSelectedType

class CreateRoutineViewModel(application: Application): AndroidViewModel(application) {

    val MINIMAL_TITLE_LENGTH: Int = 3
    val DEFAULT_TIME = "12:00"

    val title: MutableLiveData<String> = MutableLiveData(application.getString(R.string.lunch))
    val icon: MutableLiveData<IconPickerSelectedType> = MutableLiveData(IconPickerSelectedType.IC_BATH)
    val time: MutableLiveData<String> = MutableLiveData(DEFAULT_TIME)

    fun isTextValid(text: String): Boolean {
        return compareValues(text.length, MINIMAL_TITLE_LENGTH) >= 0
    }

}