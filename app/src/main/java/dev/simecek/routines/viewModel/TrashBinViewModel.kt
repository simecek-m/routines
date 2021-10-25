package dev.simecek.routines.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashBinViewModel @Inject constructor(
    private val repository: RoutineRepository
): ViewModel() {

    val softDeletedRoutines: LiveData<List<Routine>> = repository.getSoftDeletedRoutines()

    fun permanentlyRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.permanentlyDelete(routine)
        }
    }

    fun restoreRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.restore(routine)
        }
    }

}