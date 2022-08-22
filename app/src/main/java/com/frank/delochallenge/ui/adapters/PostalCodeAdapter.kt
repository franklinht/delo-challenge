package com.frank.delochallenge.ui.adapters

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frank.delochallenge.R
import com.frank.delochallenge.model.persistence.PostalCode
import com.frank.delochallenge.model.persistence.PostalCodeDatabase
import kotlinx.coroutines.runBlocking

class PostalCodeAdapter internal constructor(context: Context) : RecyclerView.Adapter<PostalCodeAdapter.ViewHolder>() {

    private val postalCodeDB by lazy { context.let { PostalCodeDatabase.getDatabase(it).postalCodeDAO() } }
    private lateinit var postalCodes : List<PostalCode>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        runBlocking {
            postalCodes = postalCodeDB.getAllPostalCodes()
        }
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_adapter_postal_code, parent, false))
    }


    override fun getItemCount(): Int = runBlocking {
        postalCodeDB.getCount()
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(postalCode: PostalCode) {
            var postalCodeNumber = itemView.findViewById<TextView>(R.id.postal_code_id_tv)
            var address = itemView.findViewById<TextView>(R.id.postal_code_address_tv)

            postalCodeNumber.text = postalCode.postal_code
            address.text = postalCode.address
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(postalCodes[position])
    }
}