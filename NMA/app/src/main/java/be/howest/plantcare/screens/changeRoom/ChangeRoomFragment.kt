package be.howest.plantcare.screens.changeRoom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.howest.plantcare.R
import be.howest.plantcare.database.TokenDatabase
import be.howest.plantcare.databinding.FragmentChangeRoomBinding
import be.howest.plantcare.screens.plantlist.PlantListViewModelFactory
import be.howest.plantcare.screens.start.StartViewModel

class ChangeRoomFragment : Fragment() {

    private lateinit var viewModel: ChangeRoomViewModel
    private lateinit var viewModelFactory: ChangeRoomViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentChangeRoomBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_room,container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        viewModelFactory = ChangeRoomViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChangeRoomViewModel::class.java)
        binding.changeRoomViewModel = viewModel
        val args = ChangeRoomFragmentArgs.fromBundle(requireArguments())
        viewModel.token.observe(viewLifecycleOwner, Observer {
            viewModel.getPlant(args.plantId)
            viewModel.getRooms()
        })
        val adapter = ChangeRoomAdapter(ChangeRoomListener { roomId ->
            viewModel.onRoomClick(roomId)
        })
        viewModel.rooms.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        viewModel.selectedRoom.observe(viewLifecycleOwner, Observer {
            binding.buttonChangeRoom.visibility = View.VISIBLE
        })
        viewModel.isChanged.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(ChangeRoomFragmentDirections.actionChangeRoomFragmentToPlantDetailFragment(args.plantId))
        })
        binding.recyclerViewRooms.adapter = adapter
        return binding.root
    }
}