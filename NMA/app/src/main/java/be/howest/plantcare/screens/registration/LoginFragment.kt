package be.howest.plantcare.screens.registration

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.howest.plantcare.R
import be.howest.plantcare.database.TokenDatabase
import be.howest.plantcare.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        viewModelFactory = LoginViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.eventLogin.observe(viewLifecycleOwner, Observer {
            if(viewModel.eventLogin.value == true){
                viewModel.onLogin()
                val email = binding.editTextEmail.text
                val password = binding.editTextPassword.text
                viewModel.login(email.toString(), password.toString())
            }
        })
        viewModel.bearerToken.observe(viewLifecycleOwner, Observer {
            if(viewModel.bearerToken.value != null){
                val token = viewModel.bearerToken.value.toString()
                Log.i("Before nav", token)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToPlantListFragment())
            }
        })
        return binding.root
    }
}