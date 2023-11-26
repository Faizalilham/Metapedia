package coding.faizal.ecommerce.presentation.verification.screen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ActivityVerificationBinding
import coding.faizal.ecommerce.presentation.help.screen.HelpActivity
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.verification.viewmodel.ResendCodeViewModel
import coding.faizal.ecommerce.utils.UiUtil
import coding.faizal.ecommerce.utils.UiUtil.showPopupMenu
import coding.faizal.ecommerce.utils.spanText
import kotlinx.coroutines.launch

class VerificationActivity : AppCompatActivity() {

    private var _binding : ActivityVerificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ResendCodeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showMenu()


        lifecycleScope.launch {
            viewModel.resendTimeLeft.collect { timeLeft ->
                binding.apply {
                    if (timeLeft > 0) {
                        val text = "Mohon tunggu $timeLeft detik untuk kirim ulang"
                        tvResendCode.text = text
                    } else {
                        val text = "Tidak mendapatkan kode? Kirim ulang"
                        tvResendCode.text = text
                        tvResendCode.spanText(tvResendCode,"Tidak mendapatkan kode? Kirim ulang",24,tvResendCode.text.toString().length,this@VerificationActivity){
                          viewModel.startCountdown()
                        }
                    }
                }
            }
        }

    }

    private fun showMenu(){
        binding.imgMore.setOnClickListener {
            showPopupMenu(
                this,
                binding.imgMore,
                R.menu.menu_help
            )
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}