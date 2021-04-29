package fr.isen.senequierandroid

import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.senequierandroid.databinding.CellDeviceBinding


class DeviceAdapter(private val listdevice: MutableList<ScanResult>) :
    RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeviceViewHolder {
        val binding = CellDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.deviceAddress.text = listdevice[position].device.address
        holder.titledevice.text = listdevice[position].device.name
    }

    fun addDevice(appareilData: ScanResult) {
        Log.d("BLEAdapter", "ajout de couille")
        if (!listdevice.contains(appareilData))
            listdevice.add(appareilData)
    }

    override fun getItemCount(): Int = listdevice.size

    class DeviceViewHolder(binding: CellDeviceBinding) : RecyclerView.ViewHolder(binding.root) {
        val titledevice: TextView = itemView.findViewById(R.id.devicetitle)
        val deviceAddress: TextView = itemView.findViewById(R.id.adresseDevice)
        val layout = itemView.findViewById<View>(R.id.cellDevice)
    }
}