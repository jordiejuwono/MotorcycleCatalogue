package com.jordju.motorcyclecatalogue.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityDetailBinding
import com.jordju.motorcyclecatalogue.ui.checkout.CheckoutActivity
import com.jordju.motorcyclecatalogue.ui.detail.slideradapter.ImageData
import com.jordju.motorcyclecatalogue.ui.detail.slideradapter.SliderAdapter
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var adapter: SliderAdapter
    private lateinit var dots: ArrayList<TextView>

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

    private fun bindSliderData(imageUrlList: List<String>) {
        val listImage = arrayListOf<ImageData>()
        for (i in imageUrlList.indices) {
            listImage.add(
                ImageData(
                    imageUrlList[i]
                )
            )
        }
        adapter = SliderAdapter(listImage)

        binding.viewPager.apply {
            adapter = this@DetailActivity.adapter
        }
        dots = ArrayList()
        setIndicator(imageUrlList)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectedDot(position, imageUrlList)
                super.onPageSelected(position)
            }
        })
    }

    private fun selectedDot(position: Int, imageUrlList: List<String>) {
        for (i in imageUrlList.indices) {
            if (i == position) {
                dots[i].setTextColor(ContextCompat.getColor(this, R.color.primary_color))
            } else {
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.light_grey
                    )
                )
            }
        }
    }

    private fun setIndicator(imageUrlList: List<String>) {
        for (i in imageUrlList.indices) {
            dots.add(TextView(this))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 18f
            dots[i].setPadding(3, 0, 3, 0)
            binding.dotsIndicator.addView(dots[i])
        }
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

        userData?.imageList?.let { bindSliderData(it) }

        binding.apply {
            tvCategory.text = userData?.category
            tvDescription.text = userData?.description
            tvName.text = userData?.motorcycleName
            tvOilCapacity.text =
                getString(R.string.oil_capacity_format, userData?.oilTankCapacity)
            tvDisplacement.text =
                getString(R.string.displacement_format, userData?.displacement)
            tvMaxPower.text = getString(R.string.max_power_format, userData?.maximumPower)
            tvPrice.text = formatRupiah.format(userData?.price)
            btnBuy.setOnClickListener {
                val intent = Intent(this@DetailActivity, CheckoutActivity::class.java)
                intent.putExtra(CheckoutActivity.CHECKOUT_DATA, userData)
                startActivity(intent)
            }
        }

    }


    companion object {
        const val DETAIL_DATA = "DETAIL_DATA"
    }

}