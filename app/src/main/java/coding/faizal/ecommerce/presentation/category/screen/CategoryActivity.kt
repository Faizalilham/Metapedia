package coding.faizal.ecommerce.presentation.category.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.databinding.ActivityCategoryBinding
import coding.faizal.ecommerce.presentation.category.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    private var _binding : ActivityCategoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()
        val i = intent.getStringExtra("category")
        binding.tvTittle.text = "Kategori $i"
        binding.imgBack.setOnClickListener {  finish() }
    }

    private fun setViewPager(){
        val fragmentList = arrayListOf(
            CategoryFragmentShirt(),
            CategoryFragmentPants(),
            CategoryFragmentShirtFormal(),
            CategoryFragmentJacket(),
            CategoryFragmentHoodie(),
            CategoryFragmentJeans()
        )
        val viewPagerAdapter = ViewPagerAdapter(fragmentList,supportFragmentManager,lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabsLayout,binding.viewPager){tab,position ->
            when(position){
                0 -> tab.text = "Kaos"
                1 -> tab.text = "Kemeja"
                2 -> tab.text = "Celana"
                3 -> tab.text = "Jaket"
                4 -> tab.text = "Hoodie"
                5 -> tab.text = "Jeans"

            }
        }.attach()
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}