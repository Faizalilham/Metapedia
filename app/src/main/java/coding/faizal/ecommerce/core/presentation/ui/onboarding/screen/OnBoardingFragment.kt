package coding.faizal.ecommerce.core.presentation.ui.onboarding.screen

import coding.faizal.ecommerce.presentation.ui.home.screen.HomeActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coding.faizal.ecommerce.databinding.FragmentOnBoardingBinding
import coding.faizal.ecommerce.presentation.ui.onboarding.adapter.ViewPagerAdapter
import coding.faizal.ecommerce.presentation.viewmodel.onboarding.OnBoardingViewModel
import coding.faizal.ecommerce.presentation.ui.pralogin.screen.PraLoginActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var _binding : FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()
    var pressTime = 0L
    var limit = 500L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        closeOnBoarding()
        onBoardingViewModel.setBoardingKey(true)
        toLogin()

    }

    private fun toLogin(){
        binding.btnLogin.setOnClickListener {
            if (isAdded && activity != null) {
                startActivity(Intent(requireActivity(), PraLoginActivity::class.java).also {
                    requireActivity().finish()
                })
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                binding.spb.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                binding.spb.start()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }

    private fun closeOnBoarding(){
        binding.imageClose.setOnClickListener {
            if (isAdded && activity != null) {
                startActivity(Intent(requireActivity(), HomeActivity::class.java).also {
                    requireActivity().finish()
                })
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setViewPager(){
        val fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            FirstFragment()
        )

        val adapter = ViewPagerAdapter(childFragmentManager, fragmentList)

        binding.apply {
            viewPager.adapter = adapter
            spb.viewPager = viewPager
            spb.start()
            reverse.setOnClickListener { spb.previous() }
            reverse.setOnTouchListener(onTouchListener)
            skip.setOnClickListener { spb.next() }
            skip.setOnTouchListener(onTouchListener)

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}