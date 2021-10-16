package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.simecek.routines.databinding.FragmentRegistrationBinding
import dev.simecek.routines.viewModel.RegistrationViewModel
import timber.log.Timber

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val redirectToAvatarPicker = RegistrationFragmentDirections.redirectToAvatarPicker()
        val redirectToListAction = RegistrationFragmentDirections.redirectToList()
        binding.avatar.setOnClickListener {
            findNavController().navigate(redirectToAvatarPicker)
        }
        binding.registrationButton.setOnClickListener {
            findNavController().navigate(redirectToListAction)
        }
    }
}