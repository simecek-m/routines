package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.databinding.FragmentTrashBinBinding
import dev.simecek.routines.viewModel.TrashBinViewModel
import timber.log.Timber

@AndroidEntryPoint
class TrashBinFragment: Fragment() {

    lateinit var binding: FragmentTrashBinBinding
    private val viewModel: TrashBinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrashBinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSoftDeletedRoutines()
    }

    private fun loadSoftDeletedRoutines() {
        viewModel.softDeletedRoutines.observe(viewLifecycleOwner, {
            Timber.i("Deleted routines: $it")
        })
    }


}