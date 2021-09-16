package be.howest.plantcare.screens.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import be.howest.plantcare.R
import be.howest.plantcare.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel
    private lateinit var viewModelFactory: StartViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start, container, false
        )
        viewModelFactory = StartViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(StartViewModel::class.java)
        binding.startViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.eventLoginClick.observe(viewLifecycleOwner, Observer {
            if (viewModel.eventLoginClick.value == true) {
                viewModel.onLogin()
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginFragment())
            }
        })
        viewModel.eventRegisterClick.observe(viewLifecycleOwner, Observer {
            if (viewModel.eventRegisterClick.value == true) {
                viewModel.onRegister()
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToRegisterFragment())
            }
        })
        return binding.root
    }

}