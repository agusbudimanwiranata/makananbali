package com.dwipa.makananbali.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwipa.makananbali.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private var modelMain: MutableList<ModelMain> = ArrayList()
    lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //transparent background searchview
        val searchPlateId = searchData.getContext()
            .resources.getIdentifier("android:id/search_plate", null, null)

        val searchPlate = searchData.findViewById<View>(searchPlateId)
        searchPlate?.setBackgroundColor(Color.TRANSPARENT)
        searchData.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchData.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mainAdapter.filter.filter(newText)
                return true
            }
        })

        rvListMakanan.setLayoutManager(LinearLayoutManager(this))
        rvListMakanan.setHasFixedSize(true)

        fabBackTop.setOnClickListener { view: View? ->
            rvListMakanan.smoothScrollToPosition(
                0
            )
        }

        //get data json
        getListMakanan()
    }

    private fun getListMakanan() {
        try {
            val stream = assets.open("makanan_bali.json")
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val strContent = String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonObject = JSONObject(strContent)
                val jsonArray = jsonObject.getJSONArray("daftar_makanan")
                for (i in 0 until jsonArray.length()) {
                    val jsonObjectData = jsonArray.getJSONObject(i)
                    val dataApi = ModelMain()
                    dataApi.nama = jsonObjectData.getString("nama")
                    dataApi.kategori = jsonObjectData.getString("kategori")
                    dataApi.image = jsonObjectData.getString("img")
                    dataApi.asal = jsonObjectData.getString("asal")
                    dataApi.bahanbaku = jsonObjectData.getString("bahan")
                    dataApi.deskripsi = jsonObjectData.getString("deskripsi")
                    dataApi.review = jsonObjectData.getString("review")
                    modelMain.add(dataApi)
                }
                mainAdapter = MainAdapter(this, modelMain)
                rvListMakanan.adapter = mainAdapter
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } catch (ignored: IOException) {
            Toast.makeText(
                this@MainActivity,
                "Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}