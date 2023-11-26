package coding.faizal.ecommerce.presentation.onboarding.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.FragmentFirstBinding
import pt.tornelas.segmentedprogressbar.SegmentedProgressBar


class FirstFragment : Fragment() {


    private var _binding : FragmentFirstBinding? = null
    private val binding get() = _binding!!
//    private lateinit var onBoarding : OnBoardingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}