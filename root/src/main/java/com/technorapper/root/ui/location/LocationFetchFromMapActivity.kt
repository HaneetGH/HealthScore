package com.technorapper.root.ui.location

import android.Manifest
import android.app.ActionBar
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.technorapper.root.R
import com.technorapper.root.base.BaseClass
import com.technorapper.root.data.data_model.LocationTable
import com.technorapper.root.databinding.LocationFetchFromMapActivityViewBinding
import com.technorapper.root.interfaces.RecyclerViewClickListener
import com.technorapper.root.utils.DrawCustomMarker
import com.technorapper.root.utils.LocationProviderLiveData.Companion.getInstance

import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class LocationFetchFromMapActivity : BaseClass(), RecyclerViewClickListener {
    private var inflater: LayoutInflater? = null
    private var mCustomMarkerView: View? = null
    private var binding: LocationFetchFromMapActivityViewBinding? = null
    private var googleMap: GoogleMap? = null
    private var centreX = 0
    private var centreY = 0
    private var drawCustomMarker: DrawCustomMarker? = null
    private var currentLocation: Location? = null
    private val viewModel by viewModels<LocationFetchViewModel>()

    // private Prediction mAddresses;
    private var locationTable = LocationTable()
    var AUTOCOMPLETE_REQUEST_CODE = 666
    var isFromAutoComplete = false
    public override fun setBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.location_fetch_from_map_activity_view)
        // Places.initialize(applicationContext, resources.getString(R.string.GOOGLE_DIRECTION_KEY))
        initMap()
        addListener()
        drawOverlay()
    }

    fun onSearchCalled() {
        /*   val fields = listOf(
               Place.Field.ID,
               Place.Field.NAME,
               Place.Field.ADDRESS,
               Place.Field.LAT_LNG,
               Place.Field.BUSINESS_STATUS,
               Place.Field.ADDRESS_COMPONENTS
           )
           val intent = Autocomplete.IntentBuilder(
               AutocompleteActivityMode.OVERLAY, fields
           ).setCountry("IN") //NIGERIA
               .build(this)
           startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)*/
    }

    private fun showDefaultLocation() {


        // Creating a LatLng object for the current location
        val latLng = LatLng(30.1647, 77.2979)

        // Showing the current location in Google Map
        val camPos = CameraPosition.Builder()
            .target(latLng)
            .zoom(5f)
            .build()
        val camUpd3 = CameraUpdateFactory.newCameraPosition(camPos)
        googleMap!!.animateCamera(camUpd3)
        //  setTogglePosition();
    }

    private fun changeGoogleLogoLocation() {
        val googleLogo = map!!.view!!.findViewWithTag<View>("GoogleWatermark")
        val glLayoutParams = googleLogo.layoutParams as RelativeLayout.LayoutParams
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        glLayoutParams.setMargins(10, 0, 60, 100)
        googleLogo.layoutParams = glLayoutParams
    }

    private fun changeMyLocationPosition() {
        if (googleMap != null &&
            map!!.view!!.findViewById<View?>("1".toInt()) != null
        ) {
            // Get the button view
            val locationButton =
                (map!!.view!!.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
            // and next place it, on bottom right (as Google Maps app)
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 0, 60, 100)
        }
    }

    private fun setTogglePosition() {
        googleMap!!.uiSettings.isMyLocationButtonEnabled = true
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        googleMap!!.isMyLocationEnabled = true
        googleMap!!.setOnMyLocationClickListener { currentLocation ->
            isFromAutoComplete = false
            fetchAddress(LatLng(currentLocation.latitude, currentLocation.longitude))
        }
        val locationButton =
            (binding!!.root.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
        val rlp = locationButton.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 0, 150 /* divideScreenHeightBy(LocationFetchFromMapActivity.this, 3)*/)
    }

    private fun addListener() {
        binding!!.ivBack.setOnClickListener { view: View? -> onBackPressed() }
        binding!!.btnDone.setOnClickListener { view: View? ->
                viewModel.saveAddress(locationTable)

            }
        binding!!.ivSearch.setOnClickListener { onSearchCalled() }
        binding!!.addressBar.setOnClickListener { onSearchCalled() }




        viewModel.done.observe(this, {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mCustomMarkerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.view_custom_marker,
            null
        )
        super.onCreate(savedInstanceState)
    }

    var map: MapFragment? = null
    private fun initMap() {
        map = fragmentManager.findFragmentById(R.id.map) as MapFragment
        map!!.getMapAsync { googleMap: GoogleMap ->
            this.googleMap = googleMap
            showDefaultLocation()
            fetchLocation()
            activateMapListner(googleMap)
            setTogglePosition()
            changeMyLocationPosition()
            changeGoogleLogoLocation()
            googleMap.setPadding(0, 0, 0, 50)
        }
    }

    private fun fetchLocation() {
        requestLocation()
    }

    private fun requestLocation() {
        if (true) {
            getInstance(applicationContext).getMeLocation()
                .observe(this, object : Observer<Location?> {
                    override fun onChanged(location: Location?) {
                        if (location != null) {
                            currentLocation = location
                            moveMapToLocationWithNoMarker(
                                location.latitude,
                                location.longitude,
                                googleMap
                            )
                            isFromAutoComplete = false
                            fetchAddress(LatLng(location.latitude, location.longitude))
                            getInstance(applicationContext).removeObserver(this)
                        }
                    }
                })
            /*compositeDisposable.add(LocationUtil.fetchLocation(ActivityAddZone.this).subscribe(location -> {
                i++;
                if (googleMap != null && i == 2) {
                    this.currentLocation = location;
                    dismissDialog();
                    moveMapToLocationWithNoMarker(location.getLatitude(), location.getLongitude(), googleMap);
                    fetchAddress();
                    drawOverlay();
                    dismissDialog();

                }
            }));*/
        } else {
        }
    }

    private fun fetchAddress(source: LatLng) {


        val addresses: List<Address>
        val geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            source.latitude,
            source.longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        if (addresses != null && addresses.isNotEmpty()) {
            val address: String =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            try {

                val city: String = addresses[0].locality
                val state: String? = addresses[0]?.adminArea
                val country: String? = addresses[0]?.countryName
                val postalCode: String? = addresses[0]?.postalCode
                val knownName: String? =
                    addresses[0]?.featureName // Only if available else return NULL

                locationTable = LocationTable()
                locationTable.Lat = source.latitude
                locationTable.Lng = source.longitude
                locationTable.address = address
                locationTable.city = if (addresses[0].locality == null) state!! else city

                binding?.usraddress = address
            } catch (e: Exception) {
                Log.e("Location Not", e.message.toString())
                binding?.usraddress = address
            }
        }

    }

    private fun activateMapListner(googleMap: GoogleMap) {
        googleMap.setOnCameraIdleListener {
            Log.d("Khl", "idel" + googleMap.projection.visibleRegion.latLngBounds.center)
            if (!isFromAutoComplete) fetchAddress(googleMap.projection.visibleRegion.latLngBounds.center) else isFromAutoComplete =
                false
        }
        googleMap.setOnCameraMoveStartedListener { i ->
            if (i == 1) {
            }
        }
    }

    public override fun attachViewModel() {}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                isFromAutoComplete = true
                val place = Autocomplete.getPlaceFromIntent(
                    data!!
                )
                val address = place.address
                moveMapToLocationWithNoMarker(
                    place.latLng!!.latitude,
                    place.latLng!!.longitude,
                    googleMap
                )
                binding!!.usraddress = place.name + "," + address
                // do query with address
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                val status = Autocomplete.getStatusFromIntent(
                    data!!
                )
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else {
        }
    }

    override fun onClick(v: View, position: Int) {}
    private fun drawOverlay() {
        val observer = binding!!.framelayout.viewTreeObserver
        observer.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                binding!!.framelayout.viewTreeObserver.removeOnPreDrawListener(this)
                centreX = binding!!.fakemarker.x.toInt() + binding!!.fakemarker.width / 2
                centreY = (binding!!.fakemarker.y.toInt()
                        + binding!!.fakemarker.height)
                drawCustomMarker = DrawCustomMarker(
                    this@LocationFetchFromMapActivity,
                    resources.getDimension(com.intuit.sdp.R.dimen._50sdp)
                )
                val params = ViewGroup.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
                )
                binding!!.framelayout.addView(drawCustomMarker, params)
                return false
            }
        })
    }

    inner class ClickListener {
        /* public void rideType(int type) {
            binding.setTypeSelected(type);
            viewModel.setSelectedType(type);


        }*/
        fun click() {
            Toast.makeText(applicationContext, "click", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        var mMapIsTouched = false
        fun moveMapToLocationWithNoMarker(lat: Double?, log: Double?, googleMap: GoogleMap?) {
            googleMap!!.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat!!, log!!), 15f))
        }
    }
}