package dev.simecek.routines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.User
import dev.simecek.routines.database.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData(User.ANONYMOUS_USERNAME)
    var avatar: MutableLiveData<String> = MutableLiveData(User.DEFAULT_AVATAR)

    suspend fun register() {
        val user = User(name.value!!, avatar.value!!)
        repository.register(user)
    }
}
