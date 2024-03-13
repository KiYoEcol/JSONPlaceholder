package com.example.jsonplaceholder.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.jsonplaceholder.databinding.FragmentPhotoBinding
import com.example.jsonplaceholder.viewmodel.PhotoViewModel

class PhotoFragment : Fragment() {
    private val viewModel: PhotoViewModel by viewModels()
    private lateinit var binding: FragmentPhotoBinding
    private val args: PhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isProceedingUser.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingPhoto.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }
        viewModel.isProceedingPhoto.observe(viewLifecycleOwner) {
            if (it) {
                binding.containerProgress.visibility = View.VISIBLE
            } else if (viewModel.isProceedingUser.value == false) {
                binding.containerProgress.visibility = View.GONE
            }
        }
        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getUser(args.userId)
        viewModel.getPhoto(args.photoId)
    }
}