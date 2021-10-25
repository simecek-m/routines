package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.adapter.SoftDeletedRoutinesListAdapter
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.FragmentTrashBinBinding
import dev.simecek.routines.listener.ClickRoutineListener
import dev.simecek.routines.viewModel.TrashBinViewModel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TrashBinFragment: Fragment() {

    @Inject
    lateinit var adapter: SoftDeletedRoutinesListAdapter

    lateinit var binding: FragmentTrashBinBinding
    private val viewModel: TrashBinViewModel by viewModels()

    companion object {
        val SHOW_EMPTY_TRASH_BIN = TrashBinFragmentDirections.showEmptyTrashBin()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrashBinBinding.inflate(inflater, container, false)
        adapter.clickListener = object: ClickRoutineListener {
            override fun onClick(routine: Routine) {
                showDialog(routine)
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSoftDeletedRoutines()
        recyclerViewSetup()
    }

    private fun recyclerViewSetup() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun loadSoftDeletedRoutines() {
        viewModel.softDeletedRoutines.observe(viewLifecycleOwner, {
            Timber.i("Deleted routines: $it")
            if(it.isEmpty()) {
                findNavController().navigate(SHOW_EMPTY_TRASH_BIN)
            } else {
                adapter.list = ArrayList(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun showDialog(routine: Routine) {
        val restoreString = getString(R.string.restore_routine)
        val deleteString = getString(R.string.delete)
        val options = arrayOf(restoreString, deleteString)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(routine.title)
            .setItems(options) { _ , which ->
                when(options[which]) {
                    deleteString -> viewModel.permanentlyRoutine(routine)
                    restoreString -> viewModel.restoreRoutine(routine)
                }
            }
            .show()
    }


}