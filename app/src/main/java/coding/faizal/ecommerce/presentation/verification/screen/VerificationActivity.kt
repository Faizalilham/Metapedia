package coding.faizal.ecommerce.presentation.verification.screen



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityVerificationBinding
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ForgetPassword
import coding.faizal.ecommerce.domain.model.remote.register.Register
import coding.faizal.ecommerce.extensions.spanText
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.forgetpassword.screen.ForgetPasswordActivity
import coding.faizal.ecommerce.presentation.register.screen.RegisterActivity
import coding.faizal.ecommerce.presentation.verification.viewmodel.VerificationViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToNewPassword
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToRegister
import coding.faizal.ecommerce.utils.ReusableTextWatcher
import coding.faizal.ecommerce.utils.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class VerificationActivity : AppCompatActivity() {

    private var _binding : ActivityVerificationBinding? = null
    private val binding get() = _binding!!

    private val authPreferences by viewModels<AuthPreferencesViewModel>()

    private var email : String = ""
    private var otp : String = ""
    private var isRegister : Boolean = true

    private val viewModel by viewModels<VerificationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewEmail()
        resendTime()
        Toast.makeText(this@VerificationActivity, otp, Toast.LENGTH_SHORT).show()

        binding.imgBack.setOnClickListener {  finish() }

        binding.etOtp.addTextChangedListener(ReusableTextWatcher {
            viewModel.updateOtpInput(it)
        })

        verificationCode()

    }


    private fun verificationCode(){
        lifecycleScope.launch {
            viewModel.isOtpValid.collect { isValid ->
                if(isValid){
                    val otpInput = binding.etOtp.text.toString()
                    if(otp == otpInput){

                        viewModel.postVerification(otpInput)
                        UiUtil.hideKeyboard(
                            this@VerificationActivity,
                            currentFocus ?: View(this@VerificationActivity)
                        )
                        verificationResult()
                    }else{
                        binding.apply{
                            etInputOtp.apply {
                                val backgroundTint = ContextCompat.getColorStateList(this@VerificationActivity, R.color.red)
                                etOtp.backgroundTintList = backgroundTint

                                helperText = "OTP Tidak valid"
                                setHelperTextColor(ContextCompat.getColorStateList(this@VerificationActivity, R.color.red))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun verificationResult(){
        lifecycleScope.launch {
            viewModel.verificationResult.collect{ resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE

                            if(resource.data != null){
                                authPreferences.setToken(resource.data.token)
                            }

                            Toast.makeText(this@VerificationActivity, resource.message, Toast.LENGTH_SHORT).show()
                            if(otp.isNotBlank()){
                                if(isRegister) navigateToRegister(this@VerificationActivity,email) else navigateToNewPassword(this@VerificationActivity,
                                    VERIFICATION_DATA,otp)
                            }
                            finish()
                        }
                        is Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message
                            Toast.makeText(this@VerificationActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

    private fun resendTime(){
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

    private fun setupViewEmail(){
        val dataIntent = intent.getParcelableExtra<Register>(RegisterActivity.DATA_REGISTER)
        val dataIntent2 = intent.getParcelableExtra<ForgetPassword>(ForgetPasswordActivity.FORGET_PASSWORD_DATA)
        isRegister = intent.getBooleanExtra("isRegister",true)

        if (dataIntent != null || dataIntent2 != null) {
            val text = resources.getString(R.string.text_info_code_verification)

            val email: String? = when {
                dataIntent != null -> dataIntent.email
                dataIntent2 != null -> dataIntent2.email
                else -> null
            }

            val otp: String? = when {
                dataIntent != null -> dataIntent.otp
                dataIntent2 != null -> dataIntent2.otp
                else -> null
            }


            this.email = email ?: ""
            this.otp = otp ?: ""


            if (email != null) {
                val textResult = "$text $email"
                binding.tvInfoVerification.text = textResult
            }
        }
    }

    companion object{
        const val VERIFICATION_DATA = "verification_data"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}