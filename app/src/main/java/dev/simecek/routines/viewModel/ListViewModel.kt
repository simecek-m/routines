package dev.simecek.routines.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.simecek.routines.repository.RoutineRepository

class ListViewModel(application: Application): AndroidViewModel(application) {

    private val routineRepository = RoutineRepository(application)
    val routines = routineRepository.getAllRoutines()

}