package coding.faizal.ecommerce.presentation.history.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.databinding.ActivityHistorySearchBinding

class HistorySearchActivity : AppCompatActivity() {
    private var _binding : ActivityHistorySearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistorySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener{ finish()}
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}