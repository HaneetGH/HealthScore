package com.technorapper.root.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.technorapper.root.R
import com.technorapper.root.data.data_model.weather.DailyItem
import com.technorapper.root.databinding.ItemForecastBinding
import com.technorapper.root.interfaces.RecyclerViewClickListener


class ForecastListAdapter(
    var max1: Double = 0.0,
    var min1: Double = 0.0,
    var max2: Double = 0.0,
    var min2: Double = 0.0,
    var list: ArrayList<DailyItem>,
    var listener: RecyclerViewClickListener
) :
    RecyclerView.Adapter<ForecastListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ItemForecastBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_forecast,
            parent,
            false
        )
        binding.graph.setMinMaxTemp(min1, max1)
        binding.graphMin.setMinMaxTemp(min2, max2)
        return MyViewHolder(binding)
    }

    private val minTemp: Double by lazy {
        list.minByOrNull { it.temp.min }?.temp?.min ?: .0
    }

    private val maxTemp: Double by lazy {
        list.maxByOrNull { it.temp.max }?.temp?.max ?: .0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var prevForecast: DailyItem
        var currentForecast: DailyItem

        var nextForecast: DailyItem
        if (position == 0) {
            prevForecast = list.getOrNull(position)!!
            currentForecast = list[position]
            nextForecast = list.getOrNull(position + 1)!!
        } else if (position == list.size - 1) {
            prevForecast = list.getOrNull(position - 1)!!
            currentForecast = list[position]
            nextForecast = list.getOrNull(position)!!
        } else {
            prevForecast = list.getOrNull(position - 1)!!
            currentForecast = list[position]
            nextForecast = list.getOrNull(position + 1)!!
        }


        holder.binding.model = list[position]

        holder.binding.graph.prevTemp = prevForecast!!.temp.max
        holder.binding.graph.currentTemp = currentForecast!!.temp.max
        holder.binding.graph.nextTemp = nextForecast!!.temp.max

        holder.binding.graphMin.prevTemp = prevForecast!!.temp.min
        holder.binding.graphMin.currentTemp = currentForecast!!.temp.min
        holder.binding.graphMin.nextTemp = nextForecast!!.temp.min

        try {
            Picasso.get()
                .load("https://openweathermap.org/img/wn/${list[position].weather[0].icon}@2x.png")
                .into(holder.binding.ivCloud)
        } catch (e: Exception) {
            Log.e("imgurl", e.message!!)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(val binding: ItemForecastBinding) :
        ViewHolder(binding.root) {

    }
}