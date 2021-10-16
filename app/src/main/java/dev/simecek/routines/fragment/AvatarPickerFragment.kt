package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dev.simecek.routines.databinding.FragmentAvatarPickerBinding
import dev.simecek.routines.viewModel.RegistrationViewModel

class AvatarPickerFragment : Fragment() {

    private lateinit var binding: FragmentAvatarPickerBinding
    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAvatarPickerBinding.inflate(inflater, container, false)
        binding.handler = this
        return binding.root
    }

    fun pickAvatar(avatar: String) {
        viewModel.avatar.value = avatar
        findNavController().popBackStack()
    }

}