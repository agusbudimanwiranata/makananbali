package com.dwipa.makananbali.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dwipa.makananbali.R
import com.dwipa.makananbali.detail.DetailActivity
import com.dwipa.makananbali.main.MainAdapter.MainViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.list_nama_makanan.view.*



class MainAdapter(
    var context: Context,
    var modelMainList: MutableList<ModelMain>) : RecyclerView.Adapter<MainViewHolder>(), Filterable {

    var modelMainFilterList: List<ModelMain> = ArrayList(modelMainList)

    override fun getFilter(): Filter {
        return modelFilter
    }

    private val modelFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ModelMain> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(modelMainFilterList)
            } else {
                val filterPattern = constraint.toString().lowercase()
                for (modelMainFilter in modelMainFilterList) {
                    if (modelMainFilter.nama.lowercase().contains(filterPattern) ||
                        modelMainFilter.nama.lowercase().contains(filterPattern)
                    ) {
                        filteredList.add(modelMainFilter)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            modelMainList.clear()
            modelMainList.addAll(results.values as List<ModelMain>)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_nama_makanan, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = modelMainList[position]

        holder.tvNamaMakanan.text = data.nama
        holder.tvKategori.text = data.kategori

        Glide.with(context)
            .load(data.image)
            .transform(CenterCrop(), RoundedCorners(25))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageMakanan)

        //send data to detail activity
        holder.cvListMain.setOnClickListener { view: View? ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.DETAIL_MAKANAN, modelMainList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return modelMainList.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvListMain: CardView
        var tvNamaMakanan: TextView
        var tvNamaLengkap: TextView
        var tvKategori: TextView
        var imageMakanan: ImageView

        init {
            cvListMain = itemView.cvListMain
            tvNamaMakanan = itemView.tvNamaMakanan
            tvNamaLengkap = itemView.tvNamaLengkap
            tvKategori = itemView.tvKategori
            imageMakanan = itemView.imageMakanan
        }
    }

}