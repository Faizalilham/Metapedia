package coding.faizal.ecommerce.presentation.newpassword.screen


import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityNewPasswordBinding
import coding.faizal.ecommerce.presentation.forgetpassword.screen.ForgetPasswordActivity
import coding.faizal.ecommerce.presentation.newpassword.viewmodel.NewPasswordViewModel
import coding.faizal.ecommerce.presentation.verification.screen.VerificationActivity
import coding.faizal.ecommerce.utils.NavigationUtils
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToDone
import coding.faizal.ecommerce.utils.UiUtil.showPopupMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NewPasswordActivity : AppCompatActivity() {
    private var _binding : ActivityNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val newPasswordViewModel by viewModels<NewPasswordViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showMenu()

        val dataIntent = intent.getStringExtra(VerificationActivity.VERIFICATION_DATA)
        if(dataIntent != null){
            createNewPassword(dataIntent)
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

    private fun createNewPassword(intent : String){
        binding.apply {
            btnForgetPassword.setOnClickListener {
                val newPassword = etNewPassword.text.toString()
                val newPasswordRepeat = etNewRepeatPassword.text.toString()
                val backgroundTint = ContextCompat.getColorStateList(this@NewPasswordActivity, R.color.red)

                fun setValidationStyle(errorMessage: String) {
                    etNewPassword.background.setColorFilter(
                        resources.getColor(R.color.red),
                        PorterDuff.Mode.SRC_ATOP)
                    etNewRepeatPassword.background.setColorFilter(
                        resources.getColor(R.color.red),
                        PorterDuff.Mode.SRC_ATOP)

                    etInputNewPassword.apply {
                        helperText = errorMessage
                        setHelperTextColor(backgroundTint)
                    }

                    etInputNewRepeatPassword.apply {
                        helperText = errorMessage
                        setHelperTextColor(backgroundTint)
                    }
                }

                if (newPassword.length < 8 || newPasswordRepeat.length < 8) {
                    setValidationStyle("Minimal 8 karakter")
                } else if (newPassword != newPasswordRepeat) {
                    setValidationStyle("Password tidak cocok")
                } else {
                    newPasswordViewModel.newPassword(intent, newPassword)
                    newPasswordResult()
                }
            }

        }
    }

    private fun newPasswordResult(){
        lifecycleScope.launch {
            newPasswordViewModel.newPasswordResult.collect{ resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE
                            Toast.makeText(this@NewPasswordActivity, resource.message, Toast.LENGTH_SHORT).show()
                            navigateToDone(this@NewPasswordActivity)
                            finish()
                        }
                        is Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message
                            Toast.makeText(this@NewPasswordActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}