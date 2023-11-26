package coding.faizal.ecommerce.presentation.search.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.databinding.ActivitySearchBinding
import coding.faizal.ecommerce.presentation.help.screen.HelpActivity
import coding.faizal.ecommerce.presentation.home.utils.Utils

class SearchActivity : AppCompatActivity() {
    private var _binding : ActivitySearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.showCustomTooltip(
            binding.etSearch,
            this,
            binding.root,
            layoutInflater,
            "Coba cari 1 produk, yuk ~"
        )

        binding.linearTips.setOnClickListener { startActivity(Intent(this,HelpActivity::class.java)) }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}