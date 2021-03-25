package dev.simecek.routines.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RoutineRepository
): ViewModel() {

    val routines = repository.getAllRoutines()

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.delete(routine)
        }
    }

    fun restoreRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.insert(routine)
        }
    }

    suspend fun switchFinishState(id: Long) {
        repository.switchFinishState(id)
    }

    suspend fun getAllUnfinishedRoutines(): List<Routine> {
        return repository.getAllUnfinishedRoutinesAsList()
    }

}