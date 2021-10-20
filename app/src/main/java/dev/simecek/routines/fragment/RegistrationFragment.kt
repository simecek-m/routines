package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.databinding.FragmentRegistrationBinding
import dev.simecek.routines.exception.UserAlreadyExistsException
import dev.simecek.routines.state.StateManager
import dev.simecek.routines.viewModel.RegistrationViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    @Inject
    lateinit var stateManager: StateManager

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
        val redirectToListAction = RegistrationFragmentDirections.register()
        binding.avatar.setOnClickListener {
            findNavController().navigate(redirectToAvatarPicker)
        }
        binding.registrationButton.setOnClickListener {
            val userName = binding.name.text.toString()
            runBlocking {
                try {
                    viewModel.register()
                    findNavController().navigate(redirectToListAction)
                    stateManager.signIn(userName)
                } catch (ex: UserAlreadyExistsException) {
                    Snackbar.make(
                        binding.layout,
                        R.string.user_already_exists,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}