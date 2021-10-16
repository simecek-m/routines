package dev.simecek.routines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.simecek.routines.database.entity.User
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(): ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData("")
    var avatar: MutableLiveData<String> = MutableLiveData(User.DEFAULT_AVATAR)

}