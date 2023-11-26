package coding.faizal.ecommerce.presentation.onboarding.screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.FragmentSplashBinding
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.onboarding.viewmodel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {


    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val onBoarding by viewModels<OnBoardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoarding.getBoardingKey().observe(requireActivity()){
            if(it != null){
                Handler().postDelayed({
                    if(it){
                        if (isAdded && activity != null) {
                            startActivity(Intent(requireActivity(), HomeActivity::class.java).also {
                                requireActivity().finish()
                            })
                        }
                    }else{
                        Handler().postDelayed({
                            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
                        },2000)
                    }
                },2000)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}