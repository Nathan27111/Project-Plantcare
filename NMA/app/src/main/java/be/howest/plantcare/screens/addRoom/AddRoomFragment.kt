package be.howest.plantcare.screens.addRoom

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
import be.howest.plantcare.database.TokenDatabase
import be.howest.plantcare.databinding.FragmentAddRoomBinding
import be.howest.plantcare.databinding.FragmentPlantListBinding
import be.howest.plantcare.screens.addPlant.AddRoomToPlantFragmentDirections

class AddRoomFragment : Fragment() {

    private lateinit var viewModel: AddRoomViewModel
    private lateinit var viewModelFactory: AddRoomViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddRoomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_room, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        viewModelFactory = AddRoomViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddRoomViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addRoomViewModel = viewModel
        viewModel.eventAddRoom.observe(viewLifecycleOwner, Observer {
            val newRoom = binding.editTextRoomDescription.text.toString()
            if(newRoom == ""){
                Toast.makeText(context, "Please fill in a name for the room.", Toast.LENGTH_SHORT).show()
            }
            else{
                Log.i("test", "hallo")
                if(viewModel.eventAddRoom.value == true){
                    viewModel.onAddRoom()
                    viewModel.addRoom(newRoom)
                    findNavController().navigate(AddRoomFragmentDirections.actionAddRoomFragmentToPlantListFragment())
                }
            }
        })
        return binding.root
    }
}