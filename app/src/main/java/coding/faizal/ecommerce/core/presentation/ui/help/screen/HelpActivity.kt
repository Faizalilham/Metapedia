package coding.faizal.ecommerce.core.presentation.ui.help.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.ecommerce.databinding.ActivityHelpBinding
import coding.faizal.ecommerce.presentation.ui.help.adapter.HelpAdapter
import coding.faizal.ecommerce.presentation.ui.help.viewmodel.HelpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HelpActivity : AppCompatActivity() {

    private var _binding : ActivityHelpBinding? = null
    private val binding get() = _binding!!

    private val helpViewModel by viewModels<HelpViewModel>()
    private lateinit var  helpAdapter : HelpAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  =  ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycler()
        binding.tvTextWelcome.text = getGreetingMessage()
        back()

    }

    private fun setRecycler(){
        lifecycleScope.launch {
            helpViewModel.helpData.collect{ data ->
                helpAdapter = HelpAdapter(data)
                binding.rvHelp.apply {
                    adapter = helpAdapter
                    layoutManager = LinearLayoutManager(this@HelpActivity)
                }
            }
        }
        helpViewModel.getAllHelpData()
    }

    private fun back(){
        binding.imgBack.setOnClickListener { finish() }
    }

    private fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()

        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 1..10 -> "Selamat Pagi"
            in 11..15 -> "Selamat Siang"
            in 16..19 -> "Selamat Sore"
            else -> "Selamat Malam"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}