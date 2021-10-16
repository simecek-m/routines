package dev.simecek.routines.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(
    userRepository: UserRepository
): ViewModel() {

    val users = userRepository.getAll()

}