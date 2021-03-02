package dev.simecek.routines.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import kotlinx.coroutines.launch

class ListViewModel @ViewModelInject constructor(
    private val repository: RoutineRepository
): ViewModel() {

    val routines = repository.getAllRoutines()

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.delete(routine)
        }
    }

    suspend fun restoreRoutine(routine: Routine): Long {
        return repository.insert(routine)
    }

    suspend fun switchFinishState(id: Long) {
        repository.switchFinishState(id)
    }

    suspend fun getAllUnfinishedRoutines(): List<Routine> {
        return repository.getAllUnfinishedRoutinesAsList()
    }

}