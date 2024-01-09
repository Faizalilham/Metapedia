package coding.faizal.ecommerce.presentation.pralogin.screen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityPraLoginBinding
import coding.faizal.ecommerce.databinding.BottomSheetAuthBinding
import coding.faizal.ecommerce.databinding.BottomSheetHelpBinding
import coding.faizal.ecommerce.presentation.login.viewmodel.LoginViewModel
import coding.faizal.ecommerce.extensions.spanText
import coding.faizal.ecommerce.presentation.pralogin.viewmodel.PraLoginViewModel
import coding.faizal.ecommerce.presentation.praregister.screen.PraRegisterActivity
import coding.faizal.ecommerce.utils.Dialog
import coding.faizal.ecommerce.utils.NavigationUtils
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToForgetPassword
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHelp
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToLogin
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraRegister
import coding.faizal.ecommerce.utils.UiUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PraLoginActivity : AppCompatActivity() {

    private var _binding : ActivityPraLoginBinding? = null
    private val binding get() =  _binding!!

    private val loginViewModel by viewModels<PraLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPraLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.spanText(binding.text,resources.getString(R.string.not_have_account),28,34,this){ navigateToPraRegister(this) }
        binding.tvRegister.setOnClickListener { navigateToPraRegister(this) }
        val dataIntent = intent.getStringExtra(PraRegisterActivity.DATA_REGISTER)


        bottomSheet()
        back()
        handleValidation()
        doLogin()
        dataIntent(dataIntent)

    }

    private fun dataIntent(dataIntent : String?){
        if(dataIntent != null){
            binding.etEmail.setText(dataIntent)
            binding.btnLogin.isEnabled = true
        }
    }

    private fun doLogin(){
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                loginViewModel.praLogin(email)
                UiUtil.hideKeyboard(this@PraLoginActivity, currentFocus ?: View(this@PraLoginActivity))
                praLoginResult()
            }
        }
    }

    private fun praLoginResult(){
        lifecycleScope.launch {
            loginViewModel.praLoginResult.collect{ resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE
                            navigateToLogin(this@PraLoginActivity,binding.etEmail.text.toString())
                        }
                        is Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message
                            binding.apply {
                                Dialog.showDialogLogin(this@PraLoginActivity,"Email belum terdaftar","Lanjut daftar dengan email ini","Ya, Daftar",binding.etEmail.text.toString()){
                                    navigateToPraRegister(this@PraLoginActivity, binding.etEmail.text.toString())
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun handleValidation(){
        lifecycleScope.launch {
            loginViewModel.isEmailValid
                .collect { isValidEmail ->
                    binding.btnLogin.isEnabled = isValidEmail
                }
        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                loginViewModel.updateEmailInput(s.toString())
            }
        })
    }

    private fun back(){
        binding.imgBack.setOnClickListener {
            navigateToHome(this)
            finish()
        }
    }


    private fun bottomSheet(){
        val bottomSheet = BottomSheetDialog(this)
        binding.loginGoogle.setOnClickListener {

            val view = BottomSheetAuthBinding.inflate(layoutInflater)
            bottomSheet.apply {
                view.apply {
                    setContentView(root)
                    show()
                    imgClose.setOnClickListener { bottomSheet.dismiss() }
                    loginGoogle.setOnClickListener {
                        Toast.makeText(this@PraLoginActivity, "Login With Google", Toast.LENGTH_SHORT).show() }

                }
            }
        }

        binding.tvHelp.setOnClickListener {
            val view = BottomSheetHelpBinding.inflate(layoutInflater)
            bottomSheet.apply {
                view.apply {
                    setContentView(root)
                    show()
                    textAnotherHelp.spanText(textAnotherHelp,resources.getString(R.string.text_another_help),20,textAnotherHelp.text.toString().length,this@PraLoginActivity){
                        navigateToHelp(this@PraLoginActivity)
                    }
                    imgClose.setOnClickListener { bottomSheet.dismiss() }
                    btnForgetPassword.setOnClickListener {
                       navigateToForgetPassword(this@PraLoginActivity)
                    }

                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_EMAIL = "email"
    }
}