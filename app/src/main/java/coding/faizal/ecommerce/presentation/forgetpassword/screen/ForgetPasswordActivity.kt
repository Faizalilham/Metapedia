package coding.faizal.ecommerce.presentation.forgetpassword.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ActivityForgetPasswordBinding
import coding.faizal.ecommerce.presentation.verification.screen.VerificationActivity
import coding.faizal.ecommerce.utils.UiUtil.showPopupMenu

class ForgetPasswordActivity : AppCompatActivity() {

    private var _binding : ActivityForgetPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyEmail()
        showMenu()
        back()

    }

    private fun verifyEmail(){
        binding.btnForgetPassword.setOnClickListener {
            startActivity(Intent(this,VerificationActivity::class.java))
        }
    }

    private fun back(){
        binding.imgBack.setOnClickListener { finish() }
    }

    private fun showMenu(){
        binding.imgMore.setOnClickListener { showPopupMenu(this, binding.imgMore, R.menu.menu_help) }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}