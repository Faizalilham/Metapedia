package coding.faizal.ecommerce.core.presentation.ui.transaction.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.databinding.ActivityTransactionBinding

class TransactionActivity : AppCompatActivity() {
    private var _binding : ActivityTransactionBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}