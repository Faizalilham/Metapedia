package coding.faizal.ecommerce.core.presentation.ui.home.screen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.databinding.FragmentHomeBinding
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.presentation.ui.category.screen.*
import coding.faizal.ecommerce.core.presentation.ui.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.core.presentation.ui.home.adapter.CarouselAdapter
import coding.faizal.ecommerce.core.presentation.ui.home.adapter.ProductAdapter
import coding.faizal.ecommerce.core.presentation.ui.home.adapter.SlideImageAdapter
import coding.faizal.ecommerce.core.presentation.ui.home.utils.Utils.setupPageTransformer
import coding.faizal.ecommerce.core.presentation.viewmodel.product.ProductViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToCart
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToCategory
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToMenu
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraLogin
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToSearch
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var imageList: ArrayList<Int>
    private lateinit var carouselAdapter : CarouselAdapter
    private lateinit var productAdapter : ProductAdapter
    private lateinit var job: Job
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val productViewModel by viewModels<ProductViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setupVisibility()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImageSlider()
        navigation()
        init()
        startAutoScroll()
        animationPromo()
        category()
        setupVisibility()

        getAllProduct()
        seeAll()

    }

    private fun setupVisibility(){
        authPreferencesViewModel.getIsLogin().observe(requireActivity()){
            if(it != null && it != false){
                binding.viewNotLogin.visibility = View.GONE
            }else{
                binding.viewNotLogin.visibility = View.VISIBLE
            }
        }
    }

    private fun category(){
        binding.categoryMan.setOnClickListener {
            navigateToCategory(requireActivity(), MAN)
            val bundle = Bundle()
            bundle.putString("category", MAN)

            val fragmentTujuan = CategoryFragmentShirt()
            fragmentTujuan.arguments = bundle
            val fragmentTujuan2 = CategoryFragmentShirtFormal()
            fragmentTujuan2.arguments = bundle
            val fragmentTujuan3 = CategoryFragmentPants()
            fragmentTujuan3.arguments = bundle
            val fragmentTujuan4 = CategoryFragmentJeans()
            fragmentTujuan4.arguments = bundle
            val fragmentTujuan5 = CategoryFragmentJacket()
            fragmentTujuan5.arguments = bundle
            val fragmentTujuan6 = CategoryFragmentHoodie()
            fragmentTujuan6.arguments = bundle

        }
        binding.categoryWoman.setOnClickListener {
            navigateToCategory(requireActivity(), WOMAN)
            val bundle = Bundle()
            bundle.putString("category", WOMAN)

            val fragmentTujuan = CategoryFragmentShirt()
            fragmentTujuan.arguments = bundle
            val fragmentTujuan2 = CategoryFragmentShirtFormal()
            fragmentTujuan2.arguments = bundle
            val fragmentTujuan3 = CategoryFragmentPants()
            fragmentTujuan3.arguments = bundle
            val fragmentTujuan4 = CategoryFragmentJeans()
            fragmentTujuan4.arguments = bundle
            val fragmentTujuan5 = CategoryFragmentJacket()
            fragmentTujuan5.arguments = bundle
            val fragmentTujuan6 = CategoryFragmentHoodie()
            fragmentTujuan6.arguments = bundle
        }
        binding.categoryKids.setOnClickListener {
            navigateToCategory(requireActivity(), KIDS)
            val bundle = Bundle()
            bundle.putString("category", KIDS)

            val fragmentTujuan = CategoryFragmentShirt()
            fragmentTujuan.arguments = bundle
            val fragmentTujuan2 = CategoryFragmentShirtFormal()
            fragmentTujuan2.arguments = bundle
            val fragmentTujuan3 = CategoryFragmentPants()
            fragmentTujuan3.arguments = bundle
            val fragmentTujuan4 = CategoryFragmentJeans()
            fragmentTujuan4.arguments = bundle
            val fragmentTujuan5 = CategoryFragmentJacket()
            fragmentTujuan5.arguments = bundle
            val fragmentTujuan6 = CategoryFragmentHoodie()
            fragmentTujuan6.arguments = bundle
        }
    }

    private fun navigation() {
        binding.apply {
            imgCart.setOnClickListener { navigateToCart(requireActivity()) }
            etSearch.setOnClickListener { navigateToSearch(requireActivity()) }
            imgMenu.setOnClickListener {  navigateToMenu(requireActivity()) }
            btnLogin.setOnClickListener { navigateToPraLogin(requireActivity()) }
        }
    }

    private fun startAutoScroll() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(2000)
                binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
            }
        }
    }

    private fun getAllProduct(){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getAllProduct(it)
            }
        }

        lifecycleScope.launch {
            productViewModel.listProductResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        setPromo(listOf())
                        setRvProduct(listOf())
                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if(result != null){
                            setPromo(result)
                            setRvProduct(result)
                        }
                    }
                    is Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(requireActivity(), "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun seeAll(){
       binding.apply {
           tvSeeAllProduct.setOnClickListener {  navigateToSearch(requireActivity(),data = HOME_DATA) }
           tvSeeProduct.setOnClickListener {  navigateToSearch(requireActivity(),data = HOME_DATA) }
           tvSeePromo.setOnClickListener {  navigateToSearch(requireActivity(),data = HOME_DATA) }
       }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }



    private fun setPromo(data : List<Product>){
        val result = data.filter { it.featured }
        productAdapter = ProductAdapter(result,object : ProductAdapter.OnClickProduct{
            override fun showBottomNav(data : Product) {
                bottomSheet(data._id)
            }

            override fun productDetail(data : Product){
                startActivity(Intent(requireActivity(), DetailProduct::class.java).also{
                    it.putExtra(HOME_DATA,data._id)
                })
            }

        })

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireActivity(),2)
        }


    }

    private fun setRvProduct(data : List<Product>){
        productAdapter = ProductAdapter(data,object : ProductAdapter.OnClickProduct{
            override fun showBottomNav(data : Product) {
                bottomSheet(data._id)
            }

            override fun productDetail(data : Product){
                startActivity(Intent(requireActivity(), DetailProduct::class.java).also{
                    it.putExtra(HOME_DATA,data._id)
                })
            }

        })

        binding.rv.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)

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


    private fun bottomSheet(id : String){
        val bottomSheet = BottomSheetDialog(requireContext())
        val view = FavoriteBottomSheetBinding.inflate(layoutInflater)
        bottomSheet.apply {
            view.apply {
                setContentView(root)
                textFavorite.setOnClickListener {
                    authPreferencesViewModel.getToken().observe(requireActivity()){
                        if(it != null){
                            productViewModel.addWishlist("Bearer $it",id)
                        }
                    }

                    lifecycleScope.launch {
                        productViewModel.addWishlistResult.collect{ resource ->
                            when (resource) {
                                is Resource.Loading -> {
                                    Toast.makeText(requireActivity(), "Loading ...", Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Success -> {

                                    Toast.makeText(requireActivity(), "Product successfully added to favorite", Toast.LENGTH_SHORT).show()

                                }
                                is Resource.Error -> {

                                    val errorMessage = resource.message
                                    Toast.makeText(requireActivity(), "$errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
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



    companion object{
        const val  MAN = "Pria"
        const val  WOMAN = "Wanita"
        const val  KIDS = "Anak - anak"
        const val HOME_DATA = "home_data"
    }


}