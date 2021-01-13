package dev.simecek.routines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateRoutineViewModel: ViewModel() {

    val title: MutableLiveData<String> = MutableLiveData("")
    val icon: MutableLiveData<String> = MutableLiveData("ic_loop")
    val time: MutableLiveData<String> = MutableLiveData("12:00")

}