package com.technorapper.root.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.technorapper.root.R
import com.technorapper.root.data.data_model.weather.HourlyItem
import com.technorapper.root.databinding.ItemHourlyForecastBinding
import com.technorapper.root.interfaces.RecyclerViewClickListener
import com.squareup.picasso.Picasso


class HourlyForecastAdapter(
    var list: ArrayList<HourlyItem>,
    var context: Context,
    var picasso: Picasso, var listener: RecyclerViewClickListener,
) :
    RecyclerView.Adapter<HourlyForecastAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ItemHourlyForecastBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_hourly_forecast,
            parent,
            false
        )

        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.model = list[position]

        try {
            picasso.load("https://openweathermap.org/img/wn/${list[position].weather[0].icon}@2x.png")
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

    inner class MyViewHolder(val binding: ItemHourlyForecastBinding) :
        ViewHolder(binding.root) {
        //  private val binding=mBinding

    }
}