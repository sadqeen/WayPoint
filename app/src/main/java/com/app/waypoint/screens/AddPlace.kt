package com.app.waypoint.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.waypoint.model.WayPlace
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.widget.PlaceAutocomplete
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AddPlace(navController: NavHostController, viewModel: AddPlaceViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val placesClient = remember { Places.createClient(context) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }
    var searchQuery by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var placeTag by remember { mutableStateOf("") }
    var errorDescription by remember { mutableStateOf<String?>(null)}
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 16f)
    }

    val autocompleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val prediction = PlaceAutocomplete.getPredictionFromIntent(result.data!!)
            prediction?.placeId?.let { placeId ->
                val request = FetchPlaceRequest.newInstance(
                    placeId,
                    listOf(Place.Field.LOCATION, Place.Field.DISPLAY_NAME,Place.Field.FORMATTED_ADDRESS)
                )
                placesClient.fetchPlace(request).addOnSuccessListener { response ->
                    val place = response.place
                    place.location?.let { latLng ->
                        searchQuery = place.displayName ?: ""
                        address=place.formattedAddress?:""
                        selectedLocation = latLng
                        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                    }
                }
            }
        }
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val currentLatLng = LatLng(it.latitude, it.longitude)
                        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))
                    }
                }
            } catch (e: SecurityException) {
                // Handle exception
            }
        }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            hasLocationPermission = granted
        }
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = hasLocationPermission,
                tiltGesturesEnabled = false
            )
        ) {
            selectedLocation?.let { latLng ->
                Marker(
                    state = remember(latLng) { MarkerState(position = latLng) },
                    title = searchQuery
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {},
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 32.dp),
            readOnly = true,
            placeholder = { Text("Search for a place") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
            ),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                val intent = PlaceAutocomplete.createIntent(context)
                                autocompleteLauncher.launch(intent)
                            }
                        }
                    }
                }
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = placeTag,
                onValueChange = {
                    placeTag = it
                    errorDescription = null
                },
                label = { Text("Enter Place Name") },
                isError = errorDescription != null,
                supportingText = { errorDescription?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        val waypoint = WayPlace(placeTag, searchQuery, address, selectedLocation)
                        viewModel.savePlace(waypoint)
                        navController.popBackStack()
                    },
                    enabled = selectedLocation != null && placeTag.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Add Place")
                }
            }
        }
    }
}
