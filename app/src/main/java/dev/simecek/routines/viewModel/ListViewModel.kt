package dev.simecek.routines.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.simecek.routines.model.Routine
import dev.simecek.routines.repository.RoutineRepository
import kotlinx.coroutines.launch

class ListViewModel(application: Application): AndroidViewModel(application) {

    private var routines: LiveData<List<Routine>> = MutableLiveData()
    private val routineRepository = RoutineRepository(application)


    fun getAllRoutines(): LiveData<List<Routine>> {
        viewModelScope.launch {
            routines = routineRepository.getAllRoutines()
        }
        return routines
    }

}