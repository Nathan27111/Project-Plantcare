package be.howest.plantcare.screens.plantlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import be.howest.plantcare.MainActivity
import be.howest.plantcare.R
import be.howest.plantcare.database.TokenDatabase
import be.howest.plantcare.databinding.FragmentPlantListBinding
import be.howest.plantcare.screens.start.StartViewModel

class PlantListFragment : Fragment() {

    private lateinit var viewModel: PlantListViewModel
    private lateinit var viewModelFactory: PlantListViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPlantListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plant_list, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = TokenDatabase.getInstance(application).tokenDatabaseDao
        viewModelFactory = PlantListViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlantListViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.plantListViewModel = viewModel
        val adapter = PlantListAdapter(PlantListListener {plantId ->
            viewModel.onPlantClick(plantId)
        })
        binding.plantlist.adapter = adapter
        viewModel.token.observe(viewLifecycleOwner, Observer {
            viewModel.getPlants()
        })
        viewModel.plants.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        viewModel.navigateToPlantDetail.observe(viewLifecycleOwner, Observer {plant ->
            plant?.let {
                this.findNavController().navigate(PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plant))
            }
        })
        viewModel.refresh.observe(viewLifecycleOwner, Observer {
            viewModel.getPlants()
        })
        return binding.root
    }
}