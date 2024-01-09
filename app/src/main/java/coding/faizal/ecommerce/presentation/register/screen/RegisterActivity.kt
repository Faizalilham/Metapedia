package coding.faizal.ecommerce.presentation.register.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityRegisterBinding
import coding.faizal.ecommerce.extensions.doubleSpanText
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.done.DoneActivity
import coding.faizal.ecommerce.presentation.praregister.screen.PraRegisterActivity
import coding.faizal.ecommerce.presentation.register.viewmodel.RegisterViewModel
import coding.faizal.ecommerce.presentation.verification.screen.VerificationActivity
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToDone
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.ReusableTextWatcher
import coding.faizal.ecommerce.utils.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private var _binding : ActivityRegisterBinding? = null
    private val binding get () = _binding!!

    private val registerViewModel by viewModels<RegisterViewModel>()
    private val authPreferences by viewModels<AuthPreferencesViewModel>()

    private var dataIntent : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataIntent = intent.getStringExtra(RegisterActivity.DATA_REGISTER)
        binding.text.doubleSpanText(binding.text,resources.getString(R.string.text_sub_register),41,59,66,84,this,{
            Toast.makeText(this, "Syarat", Toast.LENGTH_SHORT).show()
        }){
            Toast.makeText(this, "Privasi", Toast.LENGTH_SHORT).show()
        }

        if(dataIntent != null){
            binding.etEmail.setText(dataIntent)
        }

        handleValidation()
        back()
        doRegister()


    }

    private fun doRegister(){
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val name = etFullname.text.toString()
                val password = etNewPassword.text.toString()
                authPreferences.getToken().observe(this@RegisterActivity){
                    if(it != null){
                        registerViewModel.postEmailRegister(it,email,name,password)
                    }
                }
                UiUtil.hideKeyboard(this@RegisterActivity, currentFocus ?: View(this@RegisterActivity))
                registerResult()
            }
        }
    }

    private fun handleValidation(){
        lifecycleScope.launch {
            registerViewModel.areFieldsValid.collect { isValid ->
                binding.btnRegister.isEnabled = isValid
            }

        }

        binding.etFullname.addTextChangedListener(ReusableTextWatcher(binding.etFullname) {
            registerViewModel.updateFullNameInput(it)
        })

        binding.etNewPassword.addTextChangedListener(ReusableTextWatcher(binding.etNewPassword) {
            registerViewModel.updatePasswordInput(it)
        })
    }

    private fun registerResult(){
        lifecycleScope.launch {
            registerViewModel.registerResult.collect{ resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE

                            Toast.makeText(this@RegisterActivity, resource.message, Toast.LENGTH_SHORT).show()
                            navigateToHome(this@RegisterActivity)
                            finish()
                        }
                        is Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message

                            Toast.makeText(this@RegisterActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }

    }

    private fun back(){
        binding.imgBack.setOnClickListener {
            navigateToHome(this)
            finish()
        }
    }

    companion object{
        const val DATA_REGISTER = "data_register"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}