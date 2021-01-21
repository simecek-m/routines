package dev.simecek.routines.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.simecek.routines.R
import dev.simecek.routines.constant.IconPickerSelectedType
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository

class CreateRoutineViewModel @ViewModelInject constructor(
    application: Application,
    private val routineRepository: RoutineRepository
): ViewModel() {

    val MINIMAL_TITLE_LENGTH: Int = 3
    val DEFAULT_TIME = "12:00"

    val title: MutableLiveData<String> = MutableLiveData(application.getString(R.string.lunch))
    val icon: MutableLiveData<IconPickerSelectedType> = MutableLiveData(IconPickerSelectedType.IC_BATH)
    val time: MutableLiveData<String> = MutableLiveData(DEFAULT_TIME)

    fun isTextValid(text: String): Boolean {
        return compareValues(text.length, MINIMAL_TITLE_LENGTH) >= 0
    }

    suspend fun createNewRoutine() {
        val routine = Routine(title = title.value!!, icon = icon.value!!, time = time.value!!)
        routineRepository.insert(routine)
    }

}