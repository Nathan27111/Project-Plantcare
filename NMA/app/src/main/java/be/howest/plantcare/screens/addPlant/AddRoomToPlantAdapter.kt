package be.howest.plantcare.screens.addPlant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import be.howest.plantcare.R
import be.howest.plantcare.databinding.RoomlistAddToPlantViewBinding
import be.howest.plantcare.generated.callback.OnClickListener
import be.howest.plantcare.network.PlantProperty
import be.howest.plantcare.network.RoomProperty
import be.howest.plantcare.screens.changeRoom.ChangeRoomAdapter

class AddRoomToPlantAdapter(val addRoomToPlantListener: AddRoomToPlantListener) :
    RecyclerView.Adapter<AddRoomToPlantAdapter.AddRoomToPlantViewHolder>() {

    private var checkedRadioButton: CompoundButton? = null

    var data = listOf<RoomProperty>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AddRoomToPlantViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.binding.radioButtonRoom.text = item.roomDescription
        holder.binding.radioButtonRoom.setOnCheckedChangeListener(checkedChangeListener)
        if (holder.binding.radioButtonRoom.isChecked) checkedRadioButton =
            holder.binding.radioButtonRoom
        holder.bind(item, addRoomToPlantListener)
    }

    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener{
        compoundButton, isChecked ->
        checkedRadioButton?.apply { setChecked(!isChecked) }
        checkedRadioButton = compoundButton.apply { setChecked(isChecked) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddRoomToPlantAdapter.AddRoomToPlantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.roomlist_add_to_plant_view, parent, false)
        return AddRoomToPlantAdapter.AddRoomToPlantViewHolder.from(parent)
    }

    class AddRoomToPlantViewHolder private constructor(val binding: RoomlistAddToPlantViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: RoomProperty, clickListener: AddRoomToPlantListener){
            binding.room = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
            val roomDescription: RadioButton = binding.radioButtonRoom
        }

        companion object{
            fun from(parent: ViewGroup): AddRoomToPlantViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RoomlistAddToPlantViewBinding.inflate(layoutInflater, parent, false)
                return AddRoomToPlantViewHolder(binding)
            }
        }
    }

}

class AddRoomToPlantListener(val clickListener: (roomId: Int) -> Unit){
    fun onClick(room: RoomProperty) = clickListener(room.roomId)
}