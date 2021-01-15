package dev.simecek.routines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.simecek.routines.constant.IconPickerSelectedType

class CreateRoutineViewModel: ViewModel() {

    val MINIMAL_TITLE_LENGTH: Int = 3

    val title: MutableLiveData<String> = MutableLiveData("")
    val icon: MutableLiveData<IconPickerSelectedType> = MutableLiveData(IconPickerSelectedType.IC_BATH)
    val time: MutableLiveData<String> = MutableLiveData("12:00")

    fun isTextValid(text: String): Boolean {
        return compareValues(text.length, MINIMAL_TITLE_LENGTH) >= 0
    }

}