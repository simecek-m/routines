package dev.simecek.routines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.simecek.routines.adapter.AccountListAdapter
import dev.simecek.routines.database.entity.User
import dev.simecek.routines.databinding.FragmentAccountListBinding
import dev.simecek.routines.listener.SelectAccountListener
import dev.simecek.routines.settings.SettingsManager
import dev.simecek.routines.viewModel.AccountListViewModel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AccountListFragment : Fragment() {

    @Inject
    lateinit var adapter: AccountListAdapter

    @Inject
    lateinit var settingsManager: SettingsManager

    private lateinit var binding: FragmentAccountListBinding
    private val viewModel: AccountListViewModel by viewModels()

    companion object {
        val EMPTY_LIST_ACTION = AccountListFragmentDirections.noAccountFound()
        val REGISTER_ACTION = AccountListFragmentDirections.register()
        val LOGIN_ACTION = AccountListFragmentDirections.login()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.selectAccountListener = selectAccountListener
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
        binding.registrationButton.setOnClickListener {
            findNavController().navigate(REGISTER_ACTION)
        }
        val user = settingsManager.getSignedInUser()
        if(user.isNotBlank()) {
            Timber.i("User is already signed in!")
            findNavController().navigate(LOGIN_ACTION)
        }
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModel.users.observe(viewLifecycleOwner, {
            if(it.isEmpty()) {
                Timber.i("Account list is empty")
                findNavController().navigate(EMPTY_LIST_ACTION)
            } else {
                Timber.i("Account list is not empty, data: $it")
                adapter.list = ArrayList(it)
            }
        })
    }

    private val selectAccountListener: SelectAccountListener = object: SelectAccountListener {
        override fun onSelect(user: User) {
            //TODO: persist logged in user state
            findNavController().navigate(LOGIN_ACTION)
        }

    }
}