package dev.simecek.routines.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmptyListViewModel @Inject constructor(
        repository: RoutineRepository
): ViewModel() {

    val routines = repository.getRoutines()

}