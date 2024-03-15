package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.jsonplaceholder.R
import com.example.jsonplaceholder.databinding.FragmentUserBinding
import com.example.jsonplaceholder.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it

            val userId = viewModel.user.value?.id ?: -1
            binding.viewPagerUser.adapter = UserViewPagerAdapter(this, userId)
            TabLayoutMediator(binding.tabLayoutUser, binding.viewPagerUser) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_user_detail)
                    1 -> tab.text = getString(R.string.title_post_list)
                    2 -> tab.text = getString(R.string.title_album_list)
                    else -> tab.text = getString(R.string.title_todo_list)
                }
            }.attach()
        }
        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getUser(args.userId)
    }
}