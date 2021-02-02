package dev.simecek.routines.fragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
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
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.FragmentListBinding
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

    private var lastDeletedRoutine: Routine? = null
    private val undoSnackbar: Snackbar by lazy {
        Snackbar.make(binding.listLayout, R.string.routine_deleted, Snackbar.LENGTH_LONG)
    }

    private val deleteIcon: Bitmap by lazy {
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_trash)!!
        icon.setTint(ContextCompat.getColor(requireContext(), R.color.error))
        icon.toBitmap(80, 80)
    }

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
                undoSnackbar.dismiss()
                val actionEmptyList = ListFragmentDirections.redirectToEmpty(lastDeletedRoutine)
                findNavController().navigate(actionEmptyList)
            } else {
                adapter.routines = it
                adapter.notifyDataSetChanged()
            }
        })
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.list)
    }

    private val swipeToDeleteCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val swipedRoutine = (adapter.list[position] as RoutineListItem.RoutineItem).routine
            listViewModel.deleteRoutine(swipedRoutine)
            lastDeletedRoutine = swipedRoutine
            undoSnackbar.setAction(R.string.undo) {
                listViewModel.restoreRoutine(swipedRoutine)
            }.show()
        }

        // disable Titles ViewHolder swipe
        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return when(viewHolder.itemViewType) {
                RoutineListAdapter.TITLE_VIEW_TYPE -> 0
                else -> return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            val view = viewHolder.itemView
            val topMargin = view.height / 2 - deleteIcon.height /2
            if(dX > deleteIcon.width) {
                c.drawBitmap(deleteIcon, view.left.toFloat(), view.top.toFloat() + topMargin, null)
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

    }

}