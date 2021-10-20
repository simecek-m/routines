package dev.simecek.routines.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.User
import dev.simecek.routines.database.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel() {

    var user: LiveData<User> = MutableLiveData(User("", ""))

    fun getUser(userName: String) {
        user = userRepository.getUserByName(userName)
    }

}