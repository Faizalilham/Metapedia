package coding.faizal.ecommerce.presentation.newpassword.screen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ActivityNewPasswordBinding
import coding.faizal.ecommerce.utils.UiUtil.showPopupMenu

class NewPasswordActivity : AppCompatActivity() {
    private var _binding : ActivityNewPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showMenu()

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