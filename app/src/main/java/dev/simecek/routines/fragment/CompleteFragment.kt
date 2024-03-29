package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.databinding.FragmentCompleteBinding

@AndroidEntryPoint
class CompleteFragment : Fragment() {

    private lateinit var binding: FragmentCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompleteBinding.inflate(inflater, container, false)
        binding.resumeButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}