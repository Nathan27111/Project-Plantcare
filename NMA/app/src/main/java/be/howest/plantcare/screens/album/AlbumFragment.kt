package be.howest.plantcare.screens.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import be.howest.plantcare.R
import be.howest.plantcare.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentAlbumBinding = DataBindingUtil.inflate<FragmentAlbumBinding>(inflater, R.layout.fragment_album, container, false)
        return binding.root
    }
}