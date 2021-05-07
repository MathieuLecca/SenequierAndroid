package fr.isen.senequierandroid

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.senequierandroid.databinding.CellDeviceBinding
import org.w3c.dom.Text


class DeviceAdapter(private val listdevice: MutableList<ScanResult>, val deviceClickCall : (BluetoothDevice)-> Unit) :
    RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeviceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellDeviceBinding.inflate(layoutInflater)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.deviceAddress.text = listdevice[position].device.address
        holder.titledevice.text = listdevice[position].device.name
        holder.layout.setOnClickListener{
            deviceClickCall (listdevice[position].device)
        }
        holder.rssi.text = listdevice[position].rssi.toString()
    }

    fun addDevice(appareilData: ScanResult) {
        Log.d("BLEAdapter", "ajout de  : ${appareilData.device.address}")
        val index = listdevice.indexOfFirst { it.device.address == appareilData.device.address }
        if (index != -1) {
            listdevice[index] = appareilData
        } else {
            listdevice.add(appareilData)
        }
    }

    override fun getItemCount(): Int = listdevice.size

    class DeviceViewHolder(binding: CellDeviceBinding) : RecyclerView.ViewHolder(binding.root) {
        val titledevice: TextView = binding.tvTitleValue
        val deviceAddress: TextView = binding.tvAddressValue
        val layout = binding.cellDevice
        val rssi: TextView = binding.tvRssiValue
    }
}