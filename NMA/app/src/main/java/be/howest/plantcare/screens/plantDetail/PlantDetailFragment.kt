package be.howest.plantcare.screens.plantDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.howest.plantcare.R
import be.howest.plantcare.database.TokenDatabase
import be.howest.plantcare.databinding.FragmentPlantDetailBinding
import be.howest.plantcare.databinding.FragmentPlantListBinding
import be.howest.plantcare.screens.plantlist.PlantListViewModel
import be.howest.plantcare.screens.plantlist.PlantListViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlantDetailFragment : Fragment() {

    private lateinit var viewModel: PlantDetailViewModel
    private lateinit var viewModelFactory: PlantDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPlantDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_plant_detail, container, false)
        val args = PlantDetailFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        viewModelFactory = PlantDetailViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlantDetailViewModel::class.java)
        binding.plantDetailViewModel = viewModel
        viewModel.token.observe(viewLifecycleOwner, Observer {
            viewModel.getPlant(args.plantId)
        })
        viewModel.plant.observe(viewLifecycleOwner, Observer {
            val plant = viewModel.plant.value
            Log.i("plant", plant.toString())
            if (plant != null) {
                viewModel.getRoom()
                binding.textViewFlowers.setText(
                    plant.flowers.toString(),
                    TextView.BufferType.EDITABLE
                )
                binding.textViewHeight.setText(
                    plant.height.toString(),
                    TextView.BufferType.EDITABLE
                )
                binding.textViewPlantDescription.setText(
                    plant.plantDescription.toString(),
                    TextView.BufferType.EDITABLE
                )
                val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                binding.textViewWater.text = calculateTime(
                    LocalDateTime.parse(plant.lastWateredAt, format),
                    plant.needsWater
                )
                if (plant.isBlooming) {
                    binding.radioButtonIsBlooming.isChecked = true
                }
            }
        })
        viewModel.room.observe(viewLifecycleOwner, Observer {
            binding.textViewRoom.text = viewModel.room.value?.roomDescription
        })
        viewModel.eventIsBlooming.observe(viewLifecycleOwner, Observer {
            binding.radioButtonIsBlooming.isChecked = viewModel.eventIsBlooming.value!!
        })
        viewModel.eventRoomClick.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(
                PlantDetailFragmentDirections.actionPlantDetailFragmentToChangeRoomFragment(
                    args.plantId
                )
            )
        })
        viewModel.eventDoneClick.observe(viewLifecycleOwner, Observer {
            val description = binding.textViewPlantDescription.text.toString()
            val flowers = binding.textViewFlowers.text.toString()
            val height = binding.textViewHeight.text.toString()
            if (description == "" || flowers == "" || height == "") {
                Toast.makeText(
                    context,
                    "Please fill in a value in each element",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.onDoneClick(description, flowers.toInt(), height.toInt())
            }
        })
        viewModel.navigate.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(
                PlantDetailFragmentDirections.actionPlantDetailFragmentToPlantListFragment(
                )
            )
        })
        viewModel.eventDeleteClick.observe(viewLifecycleOwner, Observer {
            if(viewModel.eventDeleteClick.value == true){
                binding.buttonDelete.text = getString(R.string.Cancel)
                binding.buttonConfirm.visibility = View.VISIBLE
            } else{
                binding.buttonDelete.text = getString(R.string.delete)
                binding.buttonConfirm.visibility = View.GONE
            }
        })
        viewModel.confirmed.observe(viewLifecycleOwner, Observer {
            if(viewModel.confirmed.value == true){
                findNavController().navigate(PlantDetailFragmentDirections.actionPlantDetailFragmentToPlantListFragment())
            }
        })
        return binding.root
    }

    private fun calculateTime(lastWateredAt: LocalDateTime, day: Int): String {
        val currentDateTime = LocalDateTime.now();
        if ((currentDateTime.month != lastWateredAt.month) || (currentDateTime.dayOfMonth > (lastWateredAt.dayOfMonth + day))) {
            return "Needs water!"
        } else if (currentDateTime.dayOfMonth == (lastWateredAt.dayOfMonth + day)) {
            return if (currentDateTime.hour > lastWateredAt.hour) {
                "Needs water!"
            } else if (currentDateTime.hour == lastWateredAt.hour) {
                if (currentDateTime.minute >= lastWateredAt.minute) {
                    "Needs water!"
                } else {
                    "Needs water in ${(currentDateTime.minute - lastWateredAt.minute)} minutes"
                }
            } else {
                "Needs water in ${(lastWateredAt.hour - currentDateTime.hour)} hours"
            }
        } else {
            return "Needs water in ${(lastWateredAt.dayOfMonth + day) - currentDateTime.dayOfMonth} days"
        }
    }
}