package coding.faizal.ecommerce.presentation.onboarding.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {


    private var _binding : FragmentSecondBinding? = null
    private val binding get() = _binding!!
//    private lateinit var onBoarding : OnBoardingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}