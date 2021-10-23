package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.simecek.routines.databinding.FragmentEmptyAccountListBinding

class EmptyAccountListFragment : Fragment() {

    lateinit var binding: FragmentEmptyAccountListBinding

    companion object {
        val FIRST_REGISTRATION = EmptyAccountListFragmentDirections.firtRegistration()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmptyAccountListBinding.inflate(inflater, container, false)
        binding.registrationButton.setOnClickListener {
            findNavController().navigate(FIRST_REGISTRATION)
        }
        return binding.root
    }
}