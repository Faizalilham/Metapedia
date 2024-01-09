package coding.faizal.ecommerce.presentation.praregister.screen


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
import coding.faizal.ecommerce.databinding.ActivityPraRegisterBinding
import coding.faizal.ecommerce.databinding.BottomSheetAuthBinding
import coding.faizal.ecommerce.extensions.doubleSpanText
import coding.faizal.ecommerce.presentation.praregister.viewmodel.PraRegisterViewModel
import coding.faizal.ecommerce.utils.Dialog
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraLogin
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToVerification
import coding.faizal.ecommerce.utils.UiUtil.hideKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PraRegisterActivity : AppCompatActivity() {

    private var _binding : ActivityPraRegisterBinding? = null
    private val binding get() = _binding!!

    private val praRegisterViewModel by viewModels<PraRegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPraRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.doubleSpanText(binding.text,resources.getString(R.string.text_sub_register),41,59,66,84,this,{
            Toast.makeText(this, "Syarat", Toast.LENGTH_SHORT).show()
        }){
            Toast.makeText(this, "Privasi", Toast.LENGTH_SHORT).show()
        }
        val dataIntent = intent.getStringExtra(DATA_REGISTER)


        back()
        bottomSheet()
        handleValidation()
        login()
        doRegister()
        dataIntent(dataIntent)
    }

    private fun dataIntent(dataIntent : String?){
        if(dataIntent != null){
            binding.etEmail.setText(dataIntent)
            binding.btnRegister.isEnabled = true
        }
    }

    private fun doRegister(){
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                hideKeyboard(this@PraRegisterActivity, currentFocus ?: View(this@PraRegisterActivity))

                lifecycleScope.launch {
                    praRegisterViewModel.registerResult.collect { resource ->
                        withContext(Dispatchers.Main) {
                            when (resource) {
                                is Resource.Loading -> {
                                    binding.loadingPanel.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    binding.loadingPanel.visibility = View.GONE
                                    Toast.makeText(this@PraRegisterActivity, resource.message, Toast.LENGTH_SHORT).show()

                                    navigateToVerification(
                                        this@PraRegisterActivity,
                                        DATA_REGISTER, resource.data
                                    )
                                    finish()
                                }
                                is Resource.Error -> {
                                    binding.loadingPanel.visibility = View.GONE
                                    val errorMessage = resource.message

                                    if(errorMessage.toString() == "User already exists"){
                                        Dialog.showDialogLogin(this@PraRegisterActivity,"Email sudah terdaftar","Lanjut masuk dengan email ini", "Ya, Masuk",binding.etEmail.text.toString()){
                                            navigateToPraLogin(this@PraRegisterActivity, email)
                                            finish()
                                        }
                                    }else{
                                        Toast.makeText(this@PraRegisterActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                                    }

                                }
                            }
                        }

                    }
                }

                praRegisterViewModel.postEmailRegister(email)
            }
        }
    }

    private fun handleValidation(){
        lifecycleScope.launch {
            praRegisterViewModel.isEmailValid
                .collect { isValidEmail ->
                    binding.btnRegister.isEnabled = isValidEmail
                }
        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                praRegisterViewModel.updateEmailInput(s.toString())
            }
        })
    }

    private fun back(){
        binding.imgBack.setOnClickListener {
            navigateToHome(this)
            finish()
        }
    }

    private fun login(){
        binding.tvLogin.setOnClickListener {
            navigateToPraLogin(this)
            finish()
        }
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
                        Toast.makeText(this@PraRegisterActivity, "Login With Google", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }
    }

    companion object{
        val DATA_REGISTER = "data_register"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}