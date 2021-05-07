package fr.isen.senequierandroid

import android.bluetooth.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.senequierandroid.databinding.ActivityBLESingleDeviceBinding
import fr.isen.senequierandroid.model.BLEService


class BLESingleDeviceActivity : AppCompatActivity() {

    var statut: String = "status :  "
    var bluetoothGatt: BluetoothGatt? = null

    private lateinit var binding: ActivityBLESingleDeviceBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityBLESingleDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device: BluetoothDevice? = intent.getParcelableExtra("device")

        if (device == null)
            Toast.makeText(this, "device == null", Toast.LENGTH_SHORT).show()

        connectToDevice(device)
        bluetoothGatt?.connect()

        binding.deviceName.text = device?.name ?: "Appareil Inconnu"
        binding.deviceStatus.text = "Status : en cours de connexion"



    }

    private fun connectToDevice (device: BluetoothDevice?) {
        bluetoothGatt = device?.connectGatt(this, false, gattCallback)
    }

    private val gattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    runOnUiThread {
                        statut = STATE_CONNECTED
                        binding.deviceStatus.text = "Status : " + statut
                    }
                    bluetoothGatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        statut = STATE_DISCONNECTED
                        binding.deviceStatus.text = "Status : " + statut
                    }
                }
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            runOnUiThread {
                binding.BLEServiceList.adapter?.notifyDataSetChanged()
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            runOnUiThread {
                binding.BLEServiceList.adapter?.notifyDataSetChanged()
            }
        }
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            runOnUiThread {
                binding.BLEServiceList.adapter?.notifyDataSetChanged()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            runOnUiThread {
                binding.BLEServiceList.adapter = BLEServiceAdapter(
                    gatt,
                    gatt?.services?.map {
                        BLEService(it.uuid.toString(), it.characteristics)
                    }?.toMutableList() ?: arrayListOf(), this@BLESingleDeviceActivity
                )
                binding.BLEServiceList.layoutManager = LinearLayoutManager(this@BLESingleDeviceActivity)
            }
        }
    }

    companion object {
        private const val STATE_DISCONNECTED = "Déconnecté"
        private const val STATE_CONNECTED = "Connecté"
    }
}


