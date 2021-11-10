package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.R
import dev.simecek.routines.databinding.FragmentAccountBinding
import dev.simecek.routines.state.StateManager
import dev.simecek.routines.utils.managers.ReminderManager
import dev.simecek.routines.viewModel.AccountViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {

    @Inject
    lateinit var stateManager: StateManager

    @Inject
    lateinit var reminderManager: ReminderManager

    lateinit var binding: FragmentAccountBinding
    val viewModel: AccountViewModel by viewModels()

    companion object {
        val LOGOUT_ACTION = MainFragmentDirections.logout()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val userName = stateManager.getSignedInUser()
        binding.lifecycleOwner = this
        viewModel.getUser(userName)
        binding.viewModel = viewModel
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.signOut -> {
                    logout()
                    true
                }
                R.id.delete -> {
                    delete()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

    private fun logout() {
        lifecycleScope.launch {
            val routines = viewModel.getAllActiveRoutines()
            routines.forEach { routine ->
                reminderManager.removeReminder(routine.id.toInt())
            }
            stateManager.signOut()
            val navHost = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navHost.navigate(LOGOUT_ACTION)
        }
    }

    private fun delete() {
        lifecycleScope.launch {
            viewModel.deleteAccount()
            logout()
        }
    }

}