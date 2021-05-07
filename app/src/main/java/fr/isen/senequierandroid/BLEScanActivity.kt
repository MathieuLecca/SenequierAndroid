package fr.isen.senequierandroid


import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.senequierandroid.databinding.ActivityBLEScanBinding

class BLEScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBLEScanBinding
    private var isScanning = false
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var leDeviceListAdapter: DeviceAdapter? = null
    private val handler = Handler()

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .build()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBLEScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter
        initRecyclerDevice()
        startBLEifPossible()

        binding.PlayPauseView.setOnClickListener {
            //bluetoothAdapter?.bluetoothLeScanner?.startScan(null, scanSettings, leScanCallback)
            togglePlayPauseAction()
        }

        binding.BLEScanTitle.setOnClickListener {
            togglePlayPauseAction()
        }
    }

    private fun startBLEifPossible() {
        when {
            !isDeviceHasBLESupport() || bluetoothAdapter == null -> {
                Toast.makeText(this, "Cet appareil n'est pas compatible avec le BLE", LENGTH_SHORT)
                    .show()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                //activation du ble
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
            }
            else -> {
                //on peut PAS faire le ble
                Log.d("ScanDevices", "onRequestPermissionsResult(not PERMISSION")


            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            startBLEifPossible()
        }
    }

    private fun initRecyclerDevice() {
        leDeviceListAdapter = DeviceAdapter(mutableListOf()){
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
            val intent = Intent(this, BLESingleDeviceActivity::class.java)
            intent.putExtra("device", it )
            startActivity(intent)
        }
        binding.BLERecyclerView.layoutManager = LinearLayoutManager(this)
        binding.BLERecyclerView.adapter = leDeviceListAdapter
    }

    private fun isDeviceHasBLESupport(): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Cet appareil n'est pas compatible, sorry", Toast.LENGTH_SHORT)
                .show()
        }
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000
    private fun scanLeDevice() {
        bluetoothAdapter?.bluetoothLeScanner?.let { scanner ->
            if (isScanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    isScanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                isScanning = true
                scanner.startScan(null, scanSettings, leScanCallback)
            } else {
                isScanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("BLEScan", "CC LE SCAN")
            leDeviceListAdapter?.addDevice(result)
            leDeviceListAdapter?.notifyDataSetChanged()

        }
    }

    private fun togglePlayPauseAction() {
        isScanning = !isScanning
        if (isScanning) {
            Log.d("ScanDevices", "onRequestPermON")
            binding.BLEScanTitle.text = getString(R.string.ble_scan_pause_title)
            binding.loadingProgress.isVisible = true
            binding.titleDividerNoCustom.isVisible = false
            scanLeDevice()
        } else {
            binding.BLEScanTitle.text = getString(R.string.ble_scan_play_title)
            binding.loadingProgress.isVisible = false
            binding.titleDividerNoCustom.isVisible = true
        }
    }

    companion object {
        const private val REQUEST_ENABLE_BT = 33
        const private val REQUEST_PERMISSION_LOCATION = 33
    }

}