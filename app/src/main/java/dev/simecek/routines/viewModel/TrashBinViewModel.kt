package dev.simecek.routines.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import javax.inject.Inject

@HiltViewModel
class TrashBinViewModel @Inject constructor(
    repository: RoutineRepository
): ViewModel() {

    val softDeletedRoutines: LiveData<List<Routine>> = repository.getSoftDeletedRoutines()

}