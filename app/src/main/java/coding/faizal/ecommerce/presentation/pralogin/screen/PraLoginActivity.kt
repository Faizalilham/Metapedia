package coding.faizal.ecommerce.presentation.pralogin.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ActivityPraLoginBinding
import coding.faizal.ecommerce.databinding.BottomSheetAuthBinding
import coding.faizal.ecommerce.databinding.BottomSheetHelpBinding
import coding.faizal.ecommerce.presentation.forgetpassword.screen.ForgetPasswordActivity
import coding.faizal.ecommerce.presentation.help.screen.HelpActivity
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.login.screen.LoginActivity
import coding.faizal.ecommerce.presentation.login.viewmodel.LoginViewModel
import coding.faizal.ecommerce.presentation.register.screen.RegisterActivity
import coding.faizal.ecommerce.utils.spanText
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class PraLoginActivity : AppCompatActivity() {

    private var _binding : ActivityPraLoginBinding? = null
    private val binding get() =  _binding!!

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPraLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.spanText(binding.text,resources.getString(R.string.not_have_account),28,34,this){ register() }

        bottomSheet()
        back()
        handleValidation()
        doLogin()
    }

    private fun doLogin(){
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                startActivity(Intent(this@PraLoginActivity, LoginActivity::class.java).also{
                    it.putExtra(EXTRA_EMAIL,email)
                })

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
        binding.imgBack.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java).also { finish() }) }
    }

    private fun register(){
        startActivity(Intent(this, RegisterActivity::class.java))
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
                        startActivity(Intent(this@PraLoginActivity, HelpActivity::class.java))
                    }
                    imgClose.setOnClickListener { bottomSheet.dismiss() }
                    btnForgetPassword.setOnClickListener {
                        startActivity(Intent(this@PraLoginActivity,ForgetPasswordActivity::class.java).also{finish()})
                    }

                }
            }
        }
    }

    companion object{
        val EXTRA_EMAIL = "email"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}