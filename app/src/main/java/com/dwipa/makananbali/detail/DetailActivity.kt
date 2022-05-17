package com.dwipa.makananbali.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dwipa.makananbali.R
import com.dwipa.makananbali.main.ModelMain
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var nama: String
    lateinit var kategori: String
    lateinit var asal: String
    lateinit var bahanbaku: String
    lateinit var deskripsi: String
    lateinit var review: String
    lateinit var modelMain: ModelMain

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //set transparent statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //get data intent
        modelMain = intent.getSerializableExtra(DETAIL_MAKANAN) as ModelMain
        if (modelMain != null) {
            nama = modelMain.nama
            kategori = modelMain.kategori
            asal = modelMain.asal
            bahanbaku = modelMain.bahanbaku
            deskripsi = modelMain.deskripsi
            review = modelMain.review

            Glide.with(this)
                .load(modelMain.image)
                .into(imageMakanan)

            tvNamaMakanan.setText(nama)
            tvKategori.setText(kategori)
            tvAsal.setText(asal)
            tvDeskripsi.setText(deskripsi)
            tvBahan.setText(bahanbaku)
            tvReview.setText(review)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DETAIL_MAKANAN = "DETAIL_MAKANAN"
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

}