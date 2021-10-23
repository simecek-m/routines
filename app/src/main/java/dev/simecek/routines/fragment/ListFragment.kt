package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.adapter.RoutineListAdapter
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.FragmentListBinding
import dev.simecek.routines.listener.DeleteRoutineListener
import dev.simecek.routines.listener.FinishRoutineListener
import dev.simecek.routines.utils.constant.DayPhase
import dev.simecek.routines.utils.gesture.RoutineListGestures
import dev.simecek.routines.utils.managers.ReminderManager
import dev.simecek.routines.utils.model.RoutineListItem
import dev.simecek.routines.viewModel.ListViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var adapter: RoutineListAdapter

    @Inject
    lateinit var routineListGestures: RoutineListGestures

    @Inject
    lateinit var reminderManager: ReminderManager

    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()

    companion object {
        val CREATE_NEW_ROUTINE = ListFragmentDirections.redirectToCreate()
    }

    private val deleteRoutineListener = object: DeleteRoutineListener {
        override fun onDeleteRoutineFromPosition(position: Int) {
            val swipedRoutine = (adapter.list[position] as RoutineListItem.RoutineItem).routine
            listViewModel.deleteRoutine(swipedRoutine)
        }
    }

    private val finishRoutineListener = object : FinishRoutineListener {
        override fun onFinishRoutine(id: Long) {
            lifecycleScope.launch {
                listViewModel.switchFinishState(id)
                val unfinishedRoutines = listViewModel.getAllUnfinishedRoutines()
                Timber.i("Unfinished routines: $unfinishedRoutines")
                if (unfinishedRoutines.isEmpty()) {
                    val redirectToComplete = ListFragmentDirections.redirectToComplete()
                    findNavController().navigate(redirectToComplete)
                }
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
        recyclerViewSetup()
        gesturesSetup()
        loadRoutines()
        setupToolbar()
    }

    private fun recyclerViewSetup() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        adapter.finishRoutineListener = finishRoutineListener
        binding.list.adapter = adapter
    }

    private fun gesturesSetup() {
        routineListGestures.enableSwipeToDeleteGesture(deleteRoutineListener).attachToRecyclerView(binding.list)
        binding.swipeToRefresh.setOnRefreshListener(refreshRoutinesListener)
    }

    private fun loadRoutines() {
        listViewModel.routines.removeObservers(viewLifecycleOwner)
        listViewModel.routines.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                val redirectToEmptyList = ListFragmentDirections.redirectToEmpty()
                findNavController().navigate(redirectToEmptyList)
            } else {
                adapter.updateList(getRoutineListWithTitles(it))
            }
            binding.swipeToRefresh.isRefreshing = false
        })
    }

    private fun getRoutineListWithTitles(routines: List<Routine>): ArrayList<RoutineListItem> {
        val list = ArrayList<RoutineListItem>()
        routines.forEachIndexed { index, routine ->
            if (index == 0 || routine.getDayPhase() != routines[index - 1].getDayPhase()) {
                list.add(getTitleByDatePhase(routine.getDayPhase()))
                list.add(RoutineListItem.RoutineItem(routine))
            } else {
                list.add(RoutineListItem.RoutineItem(routine))
            }
        }
        return list
    }

    private fun getTitleByDatePhase(dayPhase: DayPhase): RoutineListItem.TitleItem {
        return when(dayPhase) {
            DayPhase.MORNING -> RoutineListItem.TitleItem(requireContext().getString(R.string.morning), ContextCompat.getDrawable(requireContext(), R.drawable.ic_morning))
            DayPhase.NOON -> RoutineListItem.TitleItem(requireContext().getString(R.string.noon), ContextCompat.getDrawable(requireContext(), R.drawable.ic_noon))
            DayPhase.AFTERNOON -> RoutineListItem.TitleItem(requireContext().getString(R.string.afternoon), ContextCompat.getDrawable(requireContext(), R.drawable.ic_afternoon))
            DayPhase.EVENING -> RoutineListItem.TitleItem(requireContext().getString(R.string.evening), ContextCompat.getDrawable(requireContext(), R.drawable.ic_evening))
            DayPhase.NIGHT -> RoutineListItem.TitleItem(requireContext().getString(R.string.night), ContextCompat.getDrawable(requireContext(), R.drawable.ic_night))
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.create -> {
                    findNavController().navigate(CREATE_NEW_ROUTINE)
                    true
                }
                else -> false
            }
        }
    }

}