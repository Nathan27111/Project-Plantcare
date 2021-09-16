package be.howest.plantcare.screens.registration

import android.content.Context
import android.os.Bundle
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
import be.howest.plantcare.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerViewModelFactory: RegisterViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        registerViewModelFactory = RegisterViewModelFactory(dataSource, application)
        registerViewModel =
            ViewModelProvider(this, registerViewModelFactory).get(RegisterViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.registerViewModel = registerViewModel
        registerViewModel.eventRegister.observe(viewLifecycleOwner, Observer {
            if (registerViewModel.eventRegister.value == true) {
                registerViewModel.onRegister()
                val firstName = binding.editTextFirstName.text.toString()
                val lastName = binding.editTextLastName.text.toString()
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()
                if (firstName == "" || lastName == "" || email == "" || password == "") {
                    Toast.makeText(
                        context,
                        "Please fill in a value in each field.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    registerViewModel.register(firstName, lastName, email, password)
                    if (registerViewModel.password.value != "") {
                        binding.buttonContinue.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to register, please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
        registerViewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
            if (registerViewModel.isLoggedIn.value == true) {
                registerViewModel.onLogin()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToPlantListFragment())
            }
        })
        return binding.root
    }
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}
