package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.FragmentListBinding
import dev.simecek.routines.helper.RoutineWidgetHelper
import dev.simecek.routines.list.RoutineListAdapter
import dev.simecek.routines.listener.DeleteRoutineListener
import dev.simecek.routines.listener.FinishRoutineListener
import dev.simecek.routines.model.RoutineListItem
import dev.simecek.routines.reminder.ReminderHelper
import dev.simecek.routines.viewModel.ListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var adapter: RoutineListAdapter

    @Inject
    lateinit var routineWidgetHelper: RoutineWidgetHelper

    @Inject
    lateinit var reminderHelper: ReminderHelper

    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()
    private var lastDeletedRoutine: Routine? = null
    private val undoSnackbar: Snackbar by lazy {
        Snackbar.make(binding.listLayout, R.string.routine_deleted, Snackbar.LENGTH_LONG)
    }

    private val deleteRoutineListener = object: DeleteRoutineListener {
        override fun onDeleteRoutineFromPosition(position: Int) {
            val swipedRoutine = (adapter.list[position] as RoutineListItem.RoutineItem).routine
            listViewModel.deleteRoutine(swipedRoutine)
            reminderHelper.removeDailyReminder(swipedRoutine.id.toInt())
            lastDeletedRoutine = swipedRoutine
            undoSnackbar.setAction(R.string.undo) {
                lifecycleScope.launch {
                    val id = listViewModel.restoreRoutine(swipedRoutine)
                    reminderHelper.setDailyReminder(id.toInt(), swipedRoutine.title, swipedRoutine.reminder.hour, swipedRoutine.reminder.minute)
                }
            }.show()
        }
    }

    private val finishRoutineListener = object : FinishRoutineListener {
        override fun onFinishRoutine(routine: Routine) {
            listViewModel.finishRoutine(routine)
            if(listViewModel.getUnfinishedRoutines().isEmpty()) {
                val redirectToComplete = ListFragmentDirections.redirectToComplete()
                findNavController().navigate(redirectToComplete)
            }
        }
    }

    private val refreshRoutinesListener = SwipeRefreshLayout.OnRefreshListener {
        loadRoutines()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createButton.setOnClickListener {
            val redirectToCreate = ListFragmentDirections.redirectToCreate()
            findNavController().navigate(redirectToCreate)
        }
        recyclerViewSetup()
        gesturesSetup()
        loadRoutines()
    }

    private fun recyclerViewSetup() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        adapter.finishRoutineListener = finishRoutineListener
        binding.list.adapter = adapter
    }

    private fun gesturesSetup() {
        routineWidgetHelper.swipeToDeleteGesture(deleteRoutineListener).attachToRecyclerView(binding.list)
        binding.swipeToRefresh.setOnRefreshListener(refreshRoutinesListener)
    }

    private fun loadRoutines() {
        listViewModel.routines.removeObservers(viewLifecycleOwner)
        listViewModel.routines.observe(viewLifecycleOwner,) {
            if(it.isEmpty()) {
                undoSnackbar.dismiss()
                val actionEmptyList = ListFragmentDirections.redirectToEmpty(lastDeletedRoutine)
                findNavController().navigate(actionEmptyList)
            } else {
                adapter.routines = it
                adapter.notifyDataSetChanged()
            }
            binding.swipeToRefresh.isRefreshing = false
        }
    }

}