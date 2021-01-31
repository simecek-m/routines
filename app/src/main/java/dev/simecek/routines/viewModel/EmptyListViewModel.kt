package dev.simecek.routines.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import kotlinx.coroutines.launch

class EmptyListViewModel @ViewModelInject constructor(
        private val repository: RoutineRepository
): ViewModel() {

    val routines = repository.getAllRoutines()

    fun restoreRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.insert(routine)
        }
    }

}