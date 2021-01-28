package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.databinding.FragmentListBinding
import dev.simecek.routines.generated.callback.OnClickListener
import dev.simecek.routines.list.RoutineListAdapter
import dev.simecek.routines.model.RoutineListItem
import dev.simecek.routines.viewModel.ListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var adapter: RoutineListAdapter
    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.createButton.setOnClickListener {
            val redirectToCreate = ListFragmentDirections.redirectToCreate()
            findNavController().navigate(redirectToCreate)
        }
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listViewModel.routines.observe(viewLifecycleOwner, Observer{
            if(it.isEmpty()) {
                val actionEmptyList = ListFragmentDirections.redirectToEmpty()
                findNavController().navigate(actionEmptyList)
            } else {
                adapter.routines = it
                adapter.notifyDataSetChanged()
            }
        })
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.list)
    }

    private val swipeToDeleteCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val swipedRoutine = (adapter.list[position] as RoutineListItem.RoutineItem).routine
            listViewModel.deleteRoutine(swipedRoutine)
            Snackbar.make(binding.listLayout, R.string.routine_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo) {
                        listViewModel.restoreRoutine(swipedRoutine)
                    }
                    .show()
        }

        // disable Titles ViewHolder swipe
        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return when(viewHolder.itemViewType) {
                RoutineListAdapter.TITLE_VIEW_TYPE -> 0
                else -> return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

    }

}