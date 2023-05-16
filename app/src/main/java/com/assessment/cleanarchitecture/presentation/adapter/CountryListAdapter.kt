package com.assessment.cleanarchitecture.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.cleanarchitecture.data.model.Country
import com.assessment.cleanarchitecture.databinding.ItemCountryBinding
import javax.inject.Inject

class CountryListAdapter @Inject constructor() :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    private var dataSet: ArrayList<Country> = ArrayList()

    inner class CountryViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countryItem: Country) {
            binding.item = countryItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = dataSet.count()

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.bind(dataSet[position])

    /*Set the dataset to adapter list instance*/
    fun setData(list: List<Country>) {
        this.dataSet = list as ArrayList<Country>
        notifyDataSetChanged()
    }

}