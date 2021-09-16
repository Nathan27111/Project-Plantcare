package be.howest.plantcare.screens.changeRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked
import androidx.recyclerview.widget.RecyclerView
import be.howest.plantcare.R
import be.howest.plantcare.databinding.PlantlistViewBinding
import be.howest.plantcare.databinding.RoomlistChangeViewBinding
import be.howest.plantcare.generated.callback.OnClickListener
import be.howest.plantcare.network.PlantProperty
import be.howest.plantcare.network.RoomProperty
import be.howest.plantcare.screens.plantlist.PlantListAdapter

class ChangeRoomAdapter(val changeRoomListener: ChangeRoomListener): RecyclerView.Adapter<ChangeRoomAdapter.ChangeRoomViewHolder>() {

    private var checkedRadioButton: CompoundButton? = null

    var data = listOf<RoomProperty>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ChangeRoomViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.binding.radioButtonRoom.text = item.roomDescription
        holder.binding.radioButtonRoom.setOnCheckedChangeListener(checkedChangeListener)
        if (holder.binding.radioButtonRoom.isChecked) checkedRadioButton =
            holder.binding.radioButtonRoom
        holder.bind(item, changeRoomListener)
    }
    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener {
            compoundButton, isChecked ->
        checkedRadioButton?.apply { setChecked(!isChecked) }
        checkedRadioButton = compoundButton.apply { setChecked(isChecked) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeRoomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.roomlist_change_view, parent, false)
        return ChangeRoomViewHolder.from(parent)
    }

    class ChangeRoomViewHolder private constructor(val binding: RoomlistChangeViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: RoomProperty, clickListener: ChangeRoomListener){
            binding.room = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
            val roomDescription: RadioButton = binding.radioButtonRoom
        }

        companion object{
            fun from(parent: ViewGroup): ChangeRoomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RoomlistChangeViewBinding.inflate(layoutInflater, parent, false)
                return ChangeRoomViewHolder(binding)
            }
        }
    }
}

class ChangeRoomListener(val clickListener: (roomId: Int) -> Unit){
    fun onClick(room: RoomProperty) = clickListener(room.roomId)
}