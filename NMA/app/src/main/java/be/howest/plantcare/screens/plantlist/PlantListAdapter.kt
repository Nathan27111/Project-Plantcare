package be.howest.plantcare.screens.plantlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.howest.plantcare.R
import be.howest.plantcare.databinding.PlantlistViewBinding
import be.howest.plantcare.generated.callback.OnClickListener
import be.howest.plantcare.network.PlantProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlantListAdapter(val plantListListener: PlantListListener) : RecyclerView.Adapter<PlantListAdapter.PlantListViewHolder>() {

    var data = listOf<PlantProperty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PlantListViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.binding.textViewPlantId.text = item.plantId.toString()
        //TODO: Change plantImage based on the item's imagelink when this is implemented in the server
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        holder.binding.waterIn.text = calculateTime(
            LocalDateTime.parse(item.lastWateredAt, format),
            item.needsWater
        )
        holder.binding.plantImage.setImageResource(R.drawable.default_plant)
        holder.bind(item, plantListListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =
            layoutInflater.inflate(R.layout.plantlist_view, parent, false)
        return PlantListViewHolder.from(parent)
    }

    private fun calculateTime(lastWateredAt: LocalDateTime, day: Int): String {
        val currentDateTime = LocalDateTime.now();
        if ((currentDateTime.month != lastWateredAt.month) || (currentDateTime.dayOfMonth > (lastWateredAt.dayOfMonth + day))) {
            return "Needs water!"
        } else if (currentDateTime.dayOfMonth == (lastWateredAt.dayOfMonth + day)) {
            return if(currentDateTime.hour > lastWateredAt.hour){
                "Needs water!"
            } else if (currentDateTime.hour == lastWateredAt.hour){
                if(currentDateTime.minute >= lastWateredAt.minute){
                    "Needs water!"
                } else{
                    "Needs water in ${(currentDateTime.minute - lastWateredAt.minute)} minutes"
                }
            } else {
                "Needs water in ${(lastWateredAt.hour - currentDateTime.hour)} hours"
            }
        }
        else {
            return "Needs water in ${(lastWateredAt.dayOfMonth + day) - currentDateTime.dayOfMonth} days"
        }
    }

    class PlantListViewHolder private constructor(val binding: PlantlistViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlantProperty, clickListener: PlantListListener){
            binding.plant = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
            val plantImage: ImageView = binding.plantImage
            val timer: ImageView = binding.iconTimer
            val plantId: TextView = binding.textViewPlantId
        }


        companion object{
            fun from(parent: ViewGroup): PlantListViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlantlistViewBinding.inflate(layoutInflater, parent, false)
                return PlantListViewHolder(binding)
            }
        }
    }
}

class PlantListListener(val clickListener: (plantId: Int) -> Unit){
    fun onClick(plant: PlantProperty) = clickListener(plant.plantId)
}