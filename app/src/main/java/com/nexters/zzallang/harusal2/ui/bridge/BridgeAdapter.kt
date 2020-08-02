package com.nexters.zzallang.harusal2.ui.bridge

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.ItemBridgeBinding

class BridgeAdapter(private val context: Context) : RecyclerView.Adapter<BridgeAdapter.BridgeViewHolder>() {
    var activityList = arrayListOf<Class<out Activity>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BridgeViewHolder =
        BridgeViewHolder(DataBindingUtil
            .inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            R.layout.item_bridge,
            parent,
            false)
        )

    override fun getItemCount(): Int = activityList.size

    override fun onBindViewHolder(holder: BridgeViewHolder, position: Int) {
        holder.onBind(activityList[position])
    }

    class BridgeViewHolder(private val binding: ItemBridgeBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(activity: Class<out Activity>) {
            binding.tvActivityName.text = activity.simpleName
            binding.root.setOnClickListener {
                it.context.startActivity(Intent(it.context, activity))
            }
        }
    }
}