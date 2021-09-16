package be.howest.plantcare.screens.addPlant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.howest.plantcare.R
import be.howest.plantcare.database.TokenDatabase
import be.howest.plantcare.databinding.FragmentAddRoomToPlantBinding

class AddRoomToPlantFragment : Fragment() {

    private lateinit var viewModel: AddRoomToPlantViewModel
    private lateinit var viewModelFactory: AddRoomToPlantViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddRoomToPlantBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_room_to_plant, container, false)
        val args = AddRoomToPlantFragmentArgs.fromBundle(requireArguments())
        //TODO: connect to server and get rooms from the database to put in recyclerview
        //TODO: connect to server and use args to create a new plant
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        viewModelFactory = AddRoomToPlantViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddRoomToPlantViewModel::class.java)
        val adapter = AddRoomToPlantAdapter(AddRoomToPlantListener { roomId ->
            viewModel.onRoomClicked(roomId)
        })
        binding.recyclerViewRooms.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addRoomToPlantViewModel = viewModel
        viewModel.rooms.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        viewModel.eventRoomClicked.observe(viewLifecycleOwner, Observer {
            binding.buttonAdd.visibility = View.VISIBLE
        })
        viewModel.eventAddPlant.observe(viewLifecycleOwner, Observer {
            viewModel.addPlant(args.water, args.plantDescription, args.flowers, args.height, args.isBlooming)
        })
        viewModel.navigate.observe(viewLifecycleOwner, Observer {
            if(viewModel.navigate.value == true){
                viewModel.onNavigate()
                findNavController().navigate(AddRoomToPlantFragmentDirections.actionAddRoomToPlantFragmentToPlantListFragment())
            }
        })
        return binding.root
    }
}