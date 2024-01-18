package coding.faizal.ecommerce.core.presentation.ui.login.screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.core.presentation.ui.pralogin.screen.PraLoginActivity
import coding.faizal.ecommerce.core.presentation.viewmodel.authentication.AuthenticationViewModel
import coding.faizal.ecommerce.databinding.ActivityLoginBinding
import coding.faizal.ecommerce.databinding.BottomSheetAuthBinding
import coding.faizal.ecommerce.databinding.BottomSheetHelpBinding
import coding.faizal.ecommerce.extensions.spanText
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToForgetPassword
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHelp
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraRegister
import coding.faizal.ecommerce.utils.UiUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.spanText(binding.text,resources.getString(R.string.not_have_account),28,34,this){ navigateToPraRegister(this) }
        binding.tvRegister.setOnClickListener { navigateToPraRegister(this) }

        bottomSheet()
        back()
        handleValidation()
        doLogin()
        holdParsing()

    }

    private fun doLogin(){
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (password.isBlank()) { etPassword.error = "Password tidak boleh kosong" }
                authenticationViewModel.doLogin(email,password)
                UiUtil.hideKeyboard(this@LoginActivity, currentFocus ?: View(this@LoginActivity))
                loginResult()
            }
        }
    }

    private fun holdParsing(){
        val intent = intent.getStringExtra(PraLoginActivity.EXTRA_EMAIL)
        if(intent != null){
            binding.etEmail.apply {
                setText(intent)
                isEnabled = false
            }

        }
    }

    private fun handleValidation(){
        lifecycleScope.launch {
            authenticationViewModel.isEmailValid
                .collect { isValidEmail ->
                    binding.btnLogin.isEnabled = isValidEmail
                }

        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                authenticationViewModel.updateEmailInput(s.toString())
            }
        })

        binding.etInputEmail.setEndIconOnClickListener { finish() }
    }

    private fun loginResult(){
        lifecycleScope.launch {
            authenticationViewModel.loginResult.collect{ resource ->
                withContext(Dispatchers.Main){
                    when (resource) {
                        is coding.faizal.ecommerce.core.data.Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is coding.faizal.ecommerce.core.data.Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, resource.message, Toast.LENGTH_SHORT).show()
                            navigateToHome(this@LoginActivity)
//                            authPreferencesViewModel.setIsLogin()
                            finish()
                        }
                        is coding.faizal.ecommerce.core.data.Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message
                            Toast.makeText(this@LoginActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    }
                }

            }
        }
    }



    private fun back(){
        binding.imgBack.setOnClickListener { navigateToHome(this) }
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
                        Toast.makeText(this@LoginActivity, "Login With Google", Toast.LENGTH_SHORT).show() }

                }
            }
        }

        binding.tvHelp.setOnClickListener {
            val view = BottomSheetHelpBinding.inflate(layoutInflater)
            bottomSheet.apply {
                view.apply {
                    setContentView(root)
                    show()
                    textAnotherHelp.spanText(textAnotherHelp,resources.getString(R.string.text_another_help),20,textAnotherHelp.text.toString().length,this@LoginActivity){
                       navigateToHelp(this@LoginActivity)
                    }
                    imgClose.setOnClickListener { bottomSheet.dismiss() }
                    btnForgetPassword.setOnClickListener {
                        navigateToForgetPassword(this@LoginActivity)
                        finish()
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