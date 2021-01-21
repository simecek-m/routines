package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.databinding.FragmentListBinding
import dev.simecek.routines.viewModel.ListViewModel

@AndroidEntryPoint
class ListFragment : Fragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listViewModel.routines.observe(viewLifecycleOwner, Observer{
            if(it.isEmpty()) {
                val actionEmptyList = ListFragmentDirections.redirectToEmpty()
                findNavController().navigate(actionEmptyList)
            } else {
                binding.list.text = it.size.toString()
            }
        })
    }

}