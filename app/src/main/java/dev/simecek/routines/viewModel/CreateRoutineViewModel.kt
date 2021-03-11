package dev.simecek.routines.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.R
import dev.simecek.routines.constant.Icon
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateRoutineViewModel @Inject constructor(
    application: Application,
    private val routineRepository: RoutineRepository
): ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData(application.getString(R.string.lunch))
    val icon: MutableLiveData<Icon> = MutableLiveData(Icon.IC_LOOP)
    val reminder: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.of(LUNCH_TIME_HOUR, LUNCH_TIME_MINUTE))

    fun isTitleLongEnough(text: String): Boolean {
        return compareValues(text.length, MINIMAL_TITLE_LENGTH) >= 0
    }

    suspend fun createNewRoutine(): Long {
        val routine = Routine(title = title.value!!, icon = icon.value!!, reminder = reminder.value!!)
        return routineRepository.insert(routine)
    }

    companion object {
        const val MINIMAL_TITLE_LENGTH: Int = 3
        const val LUNCH_TIME_HOUR = 12
        const val LUNCH_TIME_MINUTE = 0
    }
}