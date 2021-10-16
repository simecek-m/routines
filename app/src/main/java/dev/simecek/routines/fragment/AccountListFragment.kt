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
import dev.simecek.routines.viewModel.AccountListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AccountListFragment : Fragment() {

    @Inject
    lateinit var adapter: AccountListAdapter

    private lateinit var binding: FragmentAccountListBinding
    private val viewModel: AccountListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerAction = AccountListFragmentDirections.register()
        adapter.selectAccountListener = selectAccountListener
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
        binding.registrationButton.setOnClickListener {
            findNavController().navigate(registerAction)
        }
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModel.users.observe(viewLifecycleOwner, {
            adapter.list = ArrayList(it)
        })
    }

    private val selectAccountListener: SelectAccountListener = object: SelectAccountListener {
        override fun onSelect(user: User) {
            //TODO: persist logged in user state
            val loginAction = AccountListFragmentDirections.login()
            findNavController().navigate(loginAction)
        }

    }
}