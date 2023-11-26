package coding.faizal.ecommerce.presentation.register.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ActivityRegisterBinding
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity
import coding.faizal.ecommerce.databinding.BottomSheetAuthBinding
import coding.faizal.ecommerce.presentation.login.screen.LoginActivity
import coding.faizal.ecommerce.presentation.pralogin.screen.PraLoginActivity
import coding.faizal.ecommerce.presentation.register.viewmodel.RegisterViewModel
import coding.faizal.ecommerce.utils.doubleSpanText
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private var _binding : ActivityRegisterBinding? = null
    private val binding get () = _binding!!


    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.doubleSpanText(binding.text,resources.getString(R.string.text_sub_register),39,59,66,84,this,{
            Toast.makeText(this, "Syarat", Toast.LENGTH_SHORT).show()
        }){
            Toast.makeText(this, "Privasi", Toast.LENGTH_SHORT).show()
        }

        back()
        bottomSheet()
        handleValidation()
        login()
    }

    private fun doRegister(){
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
//                registerViewModel.register(email)
            }
        }
    }

    private fun handleValidation(){
        lifecycleScope.launch {
            registerViewModel.isEmailValid
                .collect { isValidEmail ->
                    binding.btnRegister.isEnabled = isValidEmail
                }
        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                registerViewModel.updateEmailInput(s.toString())
            }
        })
    }

    private fun back(){
        binding.imgBack.setOnClickListener { startActivity(Intent(this,HomeActivity::class.java).also { finish() }) }
    }

    private fun login(){
       binding.tvLogin.setOnClickListener {  startActivity(Intent(this, PraLoginActivity::class.java).also{finish()}) }
    }

    private fun bottomSheet() {
        val bottomSheet = BottomSheetDialog(this)
        binding.loginGoogle.setOnClickListener {

            val view = BottomSheetAuthBinding.inflate(layoutInflater)
            bottomSheet.apply {
                view.apply {
                    setContentView(root)
                    show()
                    imgClose.setOnClickListener { bottomSheet.dismiss() }
                    loginGoogle.setOnClickListener {
                        Toast.makeText(this@RegisterActivity, "Login With Google", Toast.LENGTH_SHORT)
                            .show()
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