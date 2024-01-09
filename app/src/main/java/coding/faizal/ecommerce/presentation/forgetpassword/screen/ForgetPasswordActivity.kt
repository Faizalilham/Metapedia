package coding.faizal.ecommerce.presentation.forgetpassword.screen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityForgetPasswordBinding
import coding.faizal.ecommerce.presentation.forgetpassword.viewmodel.ForgetPasswordViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToVerification
import coding.faizal.ecommerce.utils.UiUtil
import coding.faizal.ecommerce.utils.UiUtil.showPopupMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ForgetPasswordActivity : AppCompatActivity() {

    private var _binding : ActivityForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val forgetPasswordViewModel by viewModels<ForgetPasswordViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showMenu()
        back()
        handleValidation()


    }

    private fun verifyEmail(){
        val email = binding.etEmail.text.toString()
        forgetPasswordViewModel.forgetPassword(email)
        UiUtil.hideKeyboard(this, currentFocus ?: View(this))
        forgetPasswordResult()
    }

    private fun forgetPasswordResult(){
        lifecycleScope.launch {
            forgetPasswordViewModel.forgetPasswordResult.collect{ resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE
                            Toast.makeText(this@ForgetPasswordActivity, resource.message, Toast.LENGTH_SHORT).show()
                            navigateToVerification(this@ForgetPasswordActivity,
                                FORGET_PASSWORD_DATA,resource.data,false)
                            finish()
                        }
                        is Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message
                            Toast.makeText(this@ForgetPasswordActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

    private fun handleValidation(){


        binding.btnForgetPassword.setOnClickListener {
            forgetPasswordViewModel.updateEmailInput(binding.etEmail.text.toString())
            lifecycleScope.launch {
                forgetPasswordViewModel.isEmailValid
                    .collect { isValidEmail ->
                        if(isValidEmail){
                            verifyEmail()
                        }else{
                            binding.etInputEmail.helperText = "Format email salah"
                        }


                    }
            }
        }



    }

    private fun back(){
        binding.imgBack.setOnClickListener { finish() }
    }

    private fun showMenu(){
        binding.imgMore.setOnClickListener { showPopupMenu(this, binding.imgMore, R.menu.menu_help) }
    }

    companion object{
        const val FORGET_PASSWORD_DATA = "forget_password_data"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}