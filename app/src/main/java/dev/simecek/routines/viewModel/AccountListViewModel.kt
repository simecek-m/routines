package dev.simecek.routines.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.repository.RoutineRepository
import dev.simecek.routines.database.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(
    userRepository: UserRepository,
    private val routineRepository: RoutineRepository
): ViewModel() {

    val users = userRepository.getAll()

    suspend fun getAllRoutines(): List<Routine> {
        return routineRepository.getRoutinesAsList()
    }

}