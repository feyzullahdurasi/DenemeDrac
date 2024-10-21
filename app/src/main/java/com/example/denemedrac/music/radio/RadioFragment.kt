package com.example.denemedrac.music.radio

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.denemedrac.R
import java.io.BufferedInputStream
import java.io.IOException
import java.util.*

class RadioFragment : Fragment() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var bluetoothSocket: BluetoothSocket
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var connectedDevice: BluetoothDevice? = null
    private val REQUEST_BLUETOOTH_PERMISSIONS = 101
    private var isPlaying: Boolean = false

    // Replace with the UUID for your Bluetooth service
    private val bluetoothUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_radio, container, false)

        // Request Bluetooth permissions and start the radio
        if (checkBluetoothPermissions()) {
            connectToBluetoothDevice()
        }

        return view
    }

    private fun checkBluetoothPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            // Request missing permissions
            ActivityCompat.requestPermissions(requireActivity(), missingPermissions.toTypedArray(), REQUEST_BLUETOOTH_PERMISSIONS)
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                connectToBluetoothDevice()
            } else {
                Toast.makeText(requireContext(), "Bluetooth permissions are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun connectToBluetoothDevice() {
        if (bluetoothAdapter == null) {
            Toast.makeText(requireContext(), "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(requireContext(), "Enable Bluetooth to stream radio", Toast.LENGTH_SHORT).show()
            return
        }

        // Find the device you want to connect to
        connectedDevice = bluetoothAdapter.bondedDevices.find { device ->
            device.name == "BluetoothRadio" // Replace with your device name
        }

        if (connectedDevice == null) {
            Toast.makeText(requireContext(), "Bluetooth device not found", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Connect to the Bluetooth device
            bluetoothSocket = connectedDevice!!.createRfcommSocketToServiceRecord(bluetoothUUID)
            bluetoothSocket.connect()

            // Stream audio via Bluetooth
            startStreamingAudio()

        } catch (e: Exception) {
            Log.e("Bluetooth", "Error connecting to Bluetooth device", e)
            Toast.makeText(requireContext(), "Bluetooth connection failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startStreamingAudio() {
        try {
            // Get the input stream from the Bluetooth connection
            val inputStream = BufferedInputStream(bluetoothSocket.inputStream)

            // Initialize the MediaPlayer
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
            }

            // In this example, we are not streaming directly from InputStream.
            // You need to handle Bluetooth data properly or save it as a file if necessary.
            // For Bluetooth audio stream handling, you will need to implement proper buffering techniques.
            // Here we are assuming you have Bluetooth audio data in proper format.

            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                isPlaying = true
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(requireContext(), "Radio started playing", Toast.LENGTH_SHORT).show()
                }
            }

            // You could read the audio data and write it to a local file, then play the file via MediaPlayer.
            // But Bluetooth streaming over inputStream requires specialized handling that isn't straightforward like HTTP streaming.
            // More advanced solutions can be implemented depending on your Bluetooth device and how it sends audio data.

        } catch (e: IOException) {
            Log.e("Bluetooth", "Error streaming audio", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        bluetoothSocket.close()
    }
}
