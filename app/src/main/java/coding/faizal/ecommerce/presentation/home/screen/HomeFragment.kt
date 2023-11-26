package coding.faizal.ecommerce.presentation.home.screen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.databinding.FragmentHomeBinding
import coding.faizal.ecommerce.presentation.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.presentation.home.adapter.CarouselAdapter
import coding.faizal.ecommerce.presentation.home.adapter.ProductAdapter
import coding.faizal.ecommerce.presentation.home.adapter.SlideImageAdapter
import coding.faizal.ecommerce.presentation.home.utils.Utils.setupPageTransformer
import coding.faizal.ecommerce.presentation.pralogin.screen.PraLoginActivity
import coding.faizal.ecommerce.presentation.search.screen.SearchActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.coroutines.*


class HomeFragment : Fragment() {


    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var imageList: ArrayList<Int>
    private lateinit var carouselAdapter : CarouselAdapter
    private lateinit var productAdapter : ProductAdapter
    private lateinit var job: Job


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImageSlider()
        home()
        init()
        startAutoScroll()
        setPromo()
        search()
        animationPromo()
    }

    private fun search(){
       binding.etSearch.setOnClickListener {
           val intent = Intent(activity, SearchActivity::class.java)
           startActivity(intent)
           activity?.overridePendingTransition(R.anim.slide_up, R.anim.stay)
       }
    }

    private fun home(){
        binding.btnLogin.setOnClickListener { startActivity(Intent(requireContext(),PraLoginActivity::class.java)) }
    }

    private fun startAutoScroll() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(2000)
                binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    private fun setPromo(){
        productAdapter = ProductAdapter(listOf("Data1","Data2","Data3","Data4"),object : ProductAdapter.OnClickProduct{
            override fun showBottomNav() {
                bottomSheet()
            }

            override fun productDetail(){
                startActivity(Intent(requireActivity(),DetailProduct::class.java))
            }

        })
        binding.rv.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)

        }

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireActivity(),2)
        }
    }

    private fun animationPromo(){
        binding.scrollHoriz.viewTreeObserver.addOnScrollChangedListener (
            ViewTreeObserver.OnScrollChangedListener {
                val scrollY: Int = binding.scrollHoriz.scrollX

                val alpha = 1.0f - scrollY.toFloat() / 250.0f


                if(alpha >= 0){
                    binding.linearLayout.alpha = alpha
                }

                if(alpha <= 0.5){
                    binding.linearLayout.alpha = alpha
                }

            }
        )
    }

    private fun init() {
        imageList = ArrayList()
        imageList.add(R.drawable.image_slider_one)
        imageList.add(R.drawable.image_slider_second)

        carouselAdapter = CarouselAdapter(imageList, binding.viewPager2)
        binding.viewPager2.apply {
            adapter = carouselAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(setupPageTransformer())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    job.cancel()
                    startAutoScroll()
                }
            })
        }
    }


    private fun bottomSheet(){
        val bottomSheet = BottomSheetDialog(requireContext())
        val view = FavoriteBottomSheetBinding.inflate(layoutInflater)
        bottomSheet.apply {
            view.apply {
                setContentView(root)
                textFavorite.setOnClickListener {
                    Toast.makeText(requireContext(), "Goes to favorite page", Toast.LENGTH_SHORT).show()
                }
                show()

            }
        }
    }

    private fun setupImageSlider(){
        binding.imageSlider.apply{
            setSliderAdapter(SlideImageAdapter(requireContext()))
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            indicatorSelectedColor = Color.WHITE
            indicatorUnselectedColor = Color.GRAY
            scrollTimeInSec = 4
            startAutoCycle()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}