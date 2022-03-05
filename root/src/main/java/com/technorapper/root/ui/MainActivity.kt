package com.technorapper.root.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.technorapper.root.R
import com.technorapper.root.base.BaseClass
import com.technorapper.root.constant.Task
import com.technorapper.root.data.data_model.weather.DailyItem
import com.technorapper.root.data.data_model.weather.HourlyItem
import com.technorapper.root.data.data_model.weather.WeatherForecastResponse
import com.technorapper.root.databinding.ActivityMainBinding
import com.technorapper.root.domain.DataState
import com.technorapper.root.interfaces.RecyclerViewClickListener
import com.technorapper.root.ui.adapter.ForecastListAdapter
import com.technorapper.root.ui.adapter.HourlyForecastAdapter
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseClass() {
    var progressDialog: ProgressDialog? = null

    private val viewModel by viewModels<MainActivityViewModel>()
    private var page = 1;
    lateinit var forecastListAdapter: ForecastListAdapter


    lateinit var picasso: Picasso
    private lateinit var binding: ActivityMainBinding
    var lat: Double = 0.0
    var lng: Double = 0.0
    lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    var latLng: LatLng = LatLng(0.0, 0.0)
    var inflater: LayoutInflater? = null
    var dataList: ArrayList<DailyItem> = ArrayList<DailyItem>()
    var hourlyData: ArrayList<HourlyItem> = ArrayList<HourlyItem>()
    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        getIntentData(intent)
        picasso = Picasso.Builder(this).loggingEnabled(true).build()
        setHourlyAdapter()

        viewModel.setStateEvent(
            MainStateEvent.FetchWeather(
                latLng
            )
        )
    }


    private fun getIntentData(intent: Intent?) {
        if (intent != null) {
            lat = intent.getDoubleExtra("lat", 0.0)
            lng = intent.getDoubleExtra("lng", 0.0)
            latLng = LatLng(lat, lng)
        }
    }

    private fun setHourlyAdapter() {
        hourlyForecastAdapter =
            HourlyForecastAdapter(
                hourlyData,
                this,
                picasso,
                RecyclerViewClickListener { v, position -> })




        binding.hourlyAdapter = hourlyForecastAdapter
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStop() {
        super.onStop()

    }

    override fun attachViewModel() {
        viewModel.uiState.observe(this, Observer { parse(it) })


    }

    private fun hideLoader() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    private fun showLoader() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage("Loading.....")
            progressDialog!!.show()
        }
    }

    private fun parse(it: DataState?) {
        if (it != null) {
            when (it) {
                is DataState.Success<*> -> {
                    Log.d("Api Response", "SUCCES");
                    hideLoader()
                    if (it?.data != null) {
                        when (it.task) {
                            Task.FETCH_WEATHER -> {
                                try {
                                    val response = it.data as WeatherForecastResponse
                                    var min = 0.0
                                    var max = 0.0
                                    hideLoader()
                                    if (response != null) {
                                        dataList.clear()
                                        hourlyData.clear()
                                        binding.model = response
                                        dataList.addAll(response.daily)
                                        hourlyData.addAll(response.hourly)

                                        setAdapter(response.daily)
                                        // forecastListAdapter.notifyDataSetChanged()
                                        hourlyForecastAdapter.notifyDataSetChanged()

                                        min = response.daily[0].temp.min

                                        for (dailyItem in response.daily) {
                                            if (dailyItem.temp.max > max) {
                                                max = dailyItem.temp.max
                                            }
                                            if (min > dailyItem.temp.min) {
                                                min = dailyItem.temp.min
                                            }
                                        }
                                    }
                                    if (response.current?.weather != null && response.current.weather.size > 0) {
                                        binding.weatherDesc =
                                            "${response.current.weather[0].main} ${max.toInt()}/${min.toInt()}\u2103"

                                        when (firstDigit(response.current.weather[0].id)) {
                                            2 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.thunder)
                                            }
                                            3 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.bg1)
                                            }
                                            5 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.rain_bg)
                                            }
                                            6 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.bg1)
                                            }
                                            7 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.bg1)
                                            }
                                            8 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.cloud_bg)
                                            }
                                            800 -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.clear_sky)
                                            }
                                            else -> {
                                                binding.coordinatorLayout.setBackgroundResource(R.drawable.bg1)
                                            }

                                        }
                                    }

                                } catch (e: Exception) {

                                }
                            }

                        }

                    }

                }
                is DataState.Error -> {
                    hideLoader()
                    Log.d("Api Response", "ERROR ${it.exception.toString()}");
                }
                is DataState.Loading -> {
                    showLoader()
                    Log.d("Api Response", "LOADING $it");


                }
            }
        }

    }

    private fun setAdapter(daily: ArrayList<DailyItem>) {
        var upper = ArrayList<Double>()
        var bottom = ArrayList<Double>()

        for (dailyItem in daily) {
            upper.add(dailyItem.temp.max)
            bottom.add(dailyItem.temp.min)
        }

        Collections.sort(upper)
        Collections.sort(bottom)

        var max1: Double = upper[upper.size - 1]
        var min1: Double = upper[0]
        var max2: Double = bottom[bottom.size - 1]
        var min2: Double = bottom[0]
        forecastListAdapter =
            ForecastListAdapter(
                max1,
                min1,
                max2,
                min2,
                dataList
            ) { v, position -> }
        binding.adapter = forecastListAdapter
    }

    // Find the first digit
    fun firstDigit(n: Int): Int {
        if (n == 800) {
            return 800
        }
        // Remove last digit from number
        // till only one digit is left
        var n = n
        while (n >= 10) n /= 10

        // return the first digit
        return n
    }
}
