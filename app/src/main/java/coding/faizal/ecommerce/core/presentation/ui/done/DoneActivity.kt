package coding.faizal.ecommerce.core.presentation.ui.done

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.databinding.ActivityDoneBinding
import coding.faizal.ecommerce.presentation.ui.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.ui.login.screen.LoginActivity
import coding.faizal.ecommerce.presentation.ui.pralogin.screen.PraLoginActivity

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
        binding.btnDone.setOnClickListener { startActivity(Intent(this, PraLoginActivity::class.java).also{finish()}) }
        binding.imgBack.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java).also{finish()}) }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}