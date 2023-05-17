package com.jordju.motorcyclecatalogue.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityDetailBinding
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setData() {
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_DATA, MotorcycleEntity::class.java)
        } else {
            intent.getParcelableExtra<MotorcycleEntity>(DETAIL_DATA)
        }

        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)

        supportActionBar?.title = userData?.motorcycleName

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(userData?.motorcycleImage)
                .into(ivImage)
            tvCategory.text = userData?.category
            tvDescription.text = userData?.description
            tvName.text = userData?.motorcycleName
            tvOilCapacity.text =
                getString(R.string.oil_capacity_format, userData?.oilTankCapacity)
            tvDisplacement.text =
                getString(R.string.displacement_format, userData?.displacement)
            tvMaxPower.text = getString(R.string.max_power_format, userData?.maximumPower)
            tvPrice.text = formatRupiah.format(userData?.price)
        }

    }


    companion object {
        const val DETAIL_DATA = "DETAIL_DATA"
    }

}