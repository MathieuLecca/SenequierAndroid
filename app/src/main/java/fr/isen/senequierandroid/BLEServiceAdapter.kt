package fr.isen.senequierandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.isen.senequierandroid.model.BLEService


class BLEServiceAdapter(private val serviceList: MutableList<BLEService>):
    ExpandableRecyclerViewAdapter<BLEServiceAdapter.ServiceViewHolder,
            BLEServiceAdapter.CharacteristicViewHolder>(serviceList) {

    class ServiceViewHolder(itemView: View) : GroupViewHolder(itemView) {
        val serviceCellName = itemView.findViewById<TextView>(R.id.serviceName)
        private val serviceArrow = itemView.findViewById<TextView>(R.id.serviceName)

        override fun expand() {
            super.expand()
            serviceArrow.animate().rotation(-180f).setDuration(400L).start()

        }

        override fun collapse() {
            super.collapse()
            serviceArrow.animate().rotation(0f).setDuration(400L).start()
        }
    }


    class CharacteristicViewHolder(itemView: View) : ChildViewHolder(itemView) {
        val characteristicName = itemView.findViewById<TextView>(R.id.NameCharacteristic)
        val characteristicMac = itemView.findViewById<TextView>(R.id.AdresseMac)
        val characteristicRead = itemView.findViewById<TextView>(R.id.readAction)
        val characteristicWrite = itemView.findViewById<TextView>(R.id.writeInfo)
        val characteristicNotify = itemView.findViewById<TextView>(R.id.notifyInfo)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder? {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_ble_device_cell, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacteristicViewHolder? {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_ble_device_characteristic_cell, parent, false)
        return CharacteristicViewHolder(view)
    }

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        TODO("Not yet implemented")
    }
}


/*fun onBindChildViewHolder(
    holder: ArtistViewHolder,
    flatPosition: Int,
    group: ExpandableGroup,
    childIndex: Int
) {
    val artist: Artist = (group as Genre).getItems().get(childIndex)
    holder.setArtistName(artist.getName())
}
*/