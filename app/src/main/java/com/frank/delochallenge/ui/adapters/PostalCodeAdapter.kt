package com.frank.delochallenge.ui.adapters

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frank.delochallenge.R
import com.frank.delochallenge.model.persistence.PostalCode
import java.util.Collections.addAll

class PostalCodeAdapter internal constructor(private val postalCodesList: ArrayList<PostalCode>) : RecyclerView.Adapter<PostalCodeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_adapter_postal_code, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItems(postalCodesList[position])

    override fun getItemCount(): Int = postalCodesList.size

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(postalCode: PostalCode) {
            val postalCodeNumber = itemView.findViewById<TextView>(R.id.postal_code_id_tv)
            val address = itemView.findViewById<TextView>(R.id.postal_code_address_tv)

            postalCodeNumber.text = postalCode.postal_code
            address.text = postalCode.address
        }
    }

    fun addPostalCodes(postalCodeList: List<PostalCode>) {
        postalCodesList.apply {
            clear()
            addAll(postalCodeList)
        }
    }
}