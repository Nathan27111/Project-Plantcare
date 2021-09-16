package be.howest.plantcare.screens.addPlant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import be.howest.plantcare.R
import be.howest.plantcare.databinding.FragmentAddPlantBinding

class AddPlantFragment : Fragment() {

    private lateinit var viewModel: AddPlantViewModel
    private lateinit var viewModelFactory: AddPlantViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddPlantBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_plant, container, false)
        viewModelFactory = AddPlantViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddPlantViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addPlantViewModel = viewModel
        viewModel.eventIsBloomingClick.observe(viewLifecycleOwner, Observer {
            binding.radioButtonBlooming.isChecked = viewModel.eventIsBloomingClick.value!!
        })
        viewModel.eventNext.observe(viewLifecycleOwner, Observer {
            if (viewModel.eventNext.value == true) {
                viewModel.onNext()

                val plantDescription = binding.editTextPlantDescription.text.toString()
                val water = binding.editTextNumberWater.text.toString()
                val height = binding.editTextNumberHeight.text.toString()
                val flowers = binding.editTextNumberFlowers.text.toString()
                val isBlooming = binding.radioButtonBlooming.isChecked
                if (plantDescription == "" || water == "" || height == "" || flowers == "") {
                    Toast.makeText(
                        context,
                        "Please fill in a value for each field!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(
                        AddPlantFragmentDirections.actionAddPlantFragmentToAddRoomToPlantFragment(
                            plantDescription,
                            isBlooming,
                            Integer.parseInt(water),
                            Integer.parseInt(flowers),
                            Integer.parseInt(height)
                        )
                    )
                }

            }
        })
        return binding.root
    }

}