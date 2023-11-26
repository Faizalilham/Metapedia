package coding.faizal.ecommerce.presentation.done

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.databinding.ActivityDoneBinding
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.login.screen.LoginActivity

class DoneActivity : AppCompatActivity() {
    private var _binding : ActivityDoneBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToHome()
    }

    private fun goToHome(){
        binding.btnDone.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java).also{finish()}) }
        binding.imgBack.setOnClickListener { startActivity(Intent(this,HomeActivity::class.java).also{finish()}) }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}