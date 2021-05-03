package fr.isen.senequierandroid

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.startActivity
import fr.isen.senequierandroid.databinding.ActivityBLESingleDeviceBinding
import fr.isen.senequierandroid.model.Dish

import android.bluetooth.BluetoothGatt as BluetoothGatt


class BLESingleDeviceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBLESingleDeviceBinding
    var bluetoothGatt : BluetoothGatt? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBLESingleDeviceBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice>("ble_device")
        binding.deviceName.text = device?.name ?: "appareil inconnu"
        binding.deviceStatus.text = getString(R.string.ble_device_status, getString(R.string.ble_device_status_connecting))

        connectToDevice(device)
    }

    private fun connectToDevice(device: BluetoothDevice?) {
        bluetoothGatt = device?.connectGatt(this, false, object : BluetoothGattCallback() {

            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                connectionStateChange(newState, gatt)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
            }


            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
            }
        })
    }


    private fun connectionStateChange(newState: Int, gatt: BluetoothGatt?) {
        BLEConnexionState.getBLEConnexionStateFromState(newState)?.let {
            runOnUiThread {
                binding.deviceStatus.text =
                    getString(R.string.ble_device_status, getString(it.text))
            }
        }
    }

}



