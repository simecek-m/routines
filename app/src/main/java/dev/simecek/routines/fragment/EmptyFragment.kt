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
import dev.simecek.routines.viewModel.EmptyListViewModel

@AndroidEntryPoint
class EmptyFragment : Fragment() {

    private lateinit var binding: FragmentEmptyBinding
    private val emptyListViewModel: EmptyListViewModel by viewModels()
    private val navArgs: EmptyFragmentArgs by navArgs()
    private val lastDeletedRoutine: Routine? by lazy {
        navArgs.lastDeletedRoutine
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deletedRoutine = lastDeletedRoutine
        binding.createButton.setOnClickListener {
            val createRoutineAction = EmptyFragmentDirections.redirectToCreateFirst()
            findNavController().navigate(createRoutineAction)
        }
        emptyListViewModel.routines.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()) {
                val redirectBackToList = EmptyFragmentDirections.redirectBackToList()
                findNavController().navigate(redirectBackToList)
            }
        })
        binding.restoreRoutine.setOnClickListener {
            restoreRoutine()
        }
    }

    private fun restoreRoutine() {
        if(lastDeletedRoutine != null) {
            emptyListViewModel.restoreRoutine(lastDeletedRoutine!!)
        } else {
            Snackbar.make(binding.emptyList, R.string.routine_restore_error, Snackbar.LENGTH_LONG)
        }
    }
}