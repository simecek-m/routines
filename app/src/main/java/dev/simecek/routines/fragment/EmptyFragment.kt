package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.FragmentEmptyBinding
import dev.simecek.routines.utils.managers.ReminderManager
import dev.simecek.routines.viewModel.EmptyListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class EmptyFragment : Fragment() {

    @Inject
    lateinit var reminderManager: ReminderManager

    private lateinit var binding: FragmentEmptyBinding
    private val emptyListViewModel: EmptyListViewModel by viewModels()
    private val navArgs: EmptyFragmentArgs by navArgs()
    private val lastDeletedRoutine: Routine? by lazy {
        navArgs.lastDeletedRoutine
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentEmptyBinding.inflate(inflater, container, false)
        binding.deletedRoutine = lastDeletedRoutine
        emptyListViewModel.routines.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()) {
                val redirectBackToList = EmptyFragmentDirections.redirectBackToList()
                findNavController().navigate(redirectBackToList)
            }
        })
        binding.createButton.setOnClickListener {
            redirectToCreateFirst()
        }
        binding.restoreRoutine.setOnClickListener {
            restoreRoutine()
        }
        return binding.root
    }

    private fun redirectToCreateFirst() {
        val createRoutineAction = EmptyFragmentDirections.redirectToCreateFirst()
        findNavController().navigate(createRoutineAction)
    }

    private fun restoreRoutine() {
        emptyListViewModel.restoreRoutine(lastDeletedRoutine!!)
    }

}
