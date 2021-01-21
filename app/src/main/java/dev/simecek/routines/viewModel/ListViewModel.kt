package dev.simecek.routines.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dev.simecek.routines.database.repository.RoutineRepository

class ListViewModel @ViewModelInject constructor(
    repository: RoutineRepository
): ViewModel() {

    val routines = repository.getAllRoutines()

}