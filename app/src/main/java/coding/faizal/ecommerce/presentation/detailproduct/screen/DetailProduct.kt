package coding.faizal.ecommerce.presentation.detailproduct.screen

import coding.faizal.ecommerce.utils.Dialog.showCustomDialog
import android.animation.Animator
import android.app.Application
import android.content.Intent
import android.content.res.Resources
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequest
import coding.faizal.ecommerce.databinding.ActivityDetailProductBinding
import coding.faizal.ecommerce.databinding.BottomSheetBuyBinding
import coding.faizal.ecommerce.databinding.BottomSheetDescriptionBinding
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductImage
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize
import coding.faizal.ecommerce.domain.model.remote.product.Product
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.checkout.screen.CheckoutActivity
import coding.faizal.ecommerce.presentation.detailproduct.adapter.ProductColorAdapter
import coding.faizal.ecommerce.presentation.detailproduct.adapter.ProductSizeAdapter
import coding.faizal.ecommerce.presentation.detailproduct.adapter.ViewPagerAdapter
import coding.faizal.ecommerce.presentation.detailproduct.utils.CircleAnimationUtils
import coding.faizal.ecommerce.presentation.detailproduct.viewmodel.DetailProductViewModel
import coding.faizal.ecommerce.presentation.home.adapter.ProductAdapter
import coding.faizal.ecommerce.presentation.home.screen.HomeFragment
import coding.faizal.ecommerce.presentation.home.utils.Utils
import coding.faizal.ecommerce.presentation.home.viewmodel.ProductViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToMenu
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToSearch
import coding.faizal.ecommerce.utils.UiUtil.toggleShow
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailProduct : AppCompatActivity() {

    private var _binding : ActivityDetailProductBinding? = null
    private val binding get() = _binding!!

    private val detailProductViewModel by viewModels<DetailProductViewModel>()
    private val productViewModel by viewModels<ProductViewModel>()
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()

    private lateinit var productSizeAdapter: ProductSizeAdapter
    private lateinit var productColorAdapter: ProductColorAdapter
    private lateinit var viewPagerAdapter : ViewPagerAdapter
    private lateinit var productAdapter : ProductAdapter

    private var productImage = mutableListOf<ProductImage>()

    private lateinit var  bottomSheet : BottomSheetDialog
    private var _isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheet = BottomSheetDialog(this)

        binding.scrollUp.hide()
        playingBackground()
        setEstimation()
        imageViewPager()
        headerNavigation()

        binding.shimmerViewContainer.startShimmer()

        val i = intent.getStringExtra(HomeFragment.HOME_DATA)
        if(i != null){
            getProductById(i)

            addToFavorite(i)
        }
        getAllProduct()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
        }

    }


    private fun headerNavigation(){
        binding.apply {
            imgBack.setOnClickListener {  finish() }
            imgSearch.setOnClickListener { navigateToSearch(this@DetailProduct) }
            imgMenu.setOnClickListener { navigateToMenu(this@DetailProduct,DETAIL,DETAIL) }
        }
    }

    private fun getProductById(id : String){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getProduct(it,id)
            }
        }

        lifecycleScope.launch {
            productViewModel.productResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.shimmerViewContainer.startShimmer()
                        binding.apply {
                            btnBuy.isEnabled = false
                            btnCart.isEnabled = false
                        }
                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if(result != null){
                          setupView(result)
                            bottomNavDescription(result)
                           binding.apply {
                               btnBuy.isEnabled = true
                               btnCart.isEnabled = true
                           }
                            setProductColorRecycler()
                        }
                    }
                    is Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(this@DetailProduct, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupView(i : Product){
        binding.apply {
            shimmerViewContainer.visibility = View.GONE
            binding.shimmerViewContainer.stopShimmer()
            mainContainer.visibility = View.VISIBLE
            seeAll(i)
            tvTitle.text = i.name
            tvDescription.text = i.description
            tvCategory.text = i.category
            if(i.name.contains("lengan pendek",ignoreCase = false)){
                linearLengan.visibility = View.VISIBLE
                tvLengan.text = "Lengan Pendek"
            }else if(i.name.contains("lengan panjang",ignoreCase = false)){
                linearLengan.visibility = View.VISIBLE
                tvLengan.text = "Lengan Panjang"
            }else{
                linearLengan.visibility = View.GONE
            }
            val price = "Rp${i.price}"
            tvPrice.text = price
            val quantity = "${ i.variants.sumOf { it.quantity }} items"
            tvQuantity.text = quantity
            i.variants.mapIndexed { index, productVariant ->
                if(index < i.imgSrc.size){
                    val imageUrl = i.imgSrc[index]
                    productImage.add( ProductImage(index + 1,productVariant.color,imageUrl,false),)
                    viewPagerAdapter.notifyDataSetChanged()
                    setProductColorRecycler()
                }
            }

            val listSize = mutableListOf<String>()
            i.variants.map {
                listSize.add(it.size)
            }
            setProductSizeRecycler(listSize.toList(),i)
            btnBuy.setOnClickListener {
                startActivity(Intent(this@DetailProduct,CheckoutActivity::class.java).also{
                    it.putExtra(DetailProduct.DETAIL, OrderItemRequest(i._id,"Small",1,i.price))
                })
            }

        }
    }


    private fun makeFlyAnimation(imageView : ImageView) {
        val destView = findViewById<View>(R.id.cart_relative) as RelativeLayout
        CircleAnimationUtils().attachActivity(this).setTargetView(imageView).setMoveDuration(1000)
            .setDestView(destView).setAnimationListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {

                }

                override fun onAnimationEnd(p0: Animator) {
                    showCustomDialog(this@DetailProduct)
                }

                override fun onAnimationCancel(p0: Animator) {

                }

                override fun onAnimationRepeat(p0: Animator) {

                }

            }).startAnimation()
    }

    private fun seeAll(i : Product){
        binding.seAllSize.setOnClickListener { showBottomSheetBuy(i) }
        binding.seeAllColor.setOnClickListener { showBottomSheetBuy(i) }
    }

    private fun showBottomSheetBuy(i : Product){
        if(!bottomSheet.isShowing){
            bottomNavBuy(i)
        }
    }



    private fun addToFavorite(id : String){
        binding.imgfavorite.setOnClickListener {
            _isBookmarked = !_isBookmarked
           if(_isBookmarked){
               authPreferencesViewModel.getToken().observe(this){
                   if(it != null){
                       productViewModel.addWishlist("Bearer $it",id)
                   }
               }

               lifecycleScope.launch {
                   productViewModel.addWishlistResult.collect{ resource ->
                       when (resource) {
                           is Resource.Loading -> {
                               Toast.makeText(this@DetailProduct, "Loading ...", Toast.LENGTH_SHORT).show()
                           }
                           is Resource.Success -> {
                               binding.imgfavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailProduct,
                                   R.drawable.ic_favorite  ))
                               Toast.makeText(this@DetailProduct, "Product successfully added to favorite", Toast.LENGTH_SHORT).show()

                           }
                           is Resource.Error -> {

                               val errorMessage = resource.message
                               Toast.makeText(this@DetailProduct, "$errorMessage", Toast.LENGTH_SHORT).show()
                           }
                       }
                   }
               }
           }else{
               authPreferencesViewModel.getToken().observe(this){
                   if(it != null){
                       productViewModel.remove("Bearer $it",id)
                   }
               }

               lifecycleScope.launch {
                   productViewModel.removeWishlistResult.collect{ resource ->
                       when (resource) {
                           is Resource.Loading -> {
                               Toast.makeText(this@DetailProduct, "Loading ...", Toast.LENGTH_SHORT).show()
                           }
                           is Resource.Success -> {
                               binding.imgfavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailProduct,
                                   R.drawable.ic_unfavorite  ))
                               Toast.makeText(this@DetailProduct, "Product successfully deleted from favorite", Toast.LENGTH_SHORT).show()

                           }
                           is Resource.Error -> {

                               val errorMessage = resource.message
                               Toast.makeText(this@DetailProduct, "$errorMessage", Toast.LENGTH_SHORT).show()
                           }
                       }
                   }
               }
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
                        setRvProduct(listOf())
                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if (result != null) {
                            val man = result.any { it.name.contains("pria", ignoreCase = true) && (it.name.contains("kemeja", ignoreCase = true) || it.name.contains("baju", ignoreCase = true) || it.name.contains("celana", ignoreCase = true)) }
                            val woman = result.any { it.name.contains("wanita", ignoreCase = true) && (it.name.contains("baju", ignoreCase = true) || it.name.contains("celana", ignoreCase = true)) }
                            val kid = result.any { it.name.contains("anak", ignoreCase = true) && (it.name.contains("baju", ignoreCase = true) || it.name.contains("celana", ignoreCase = true)) }

                            val finalResult = result.filter {
                                (man && (it.name.contains("pria", ignoreCase = true) && (it.name.contains("kemeja", ignoreCase = true) || it.name.contains("baju", ignoreCase = true) || it.name.contains("celana", ignoreCase = true)))) ||
                                        (woman && (it.name.contains("wanita", ignoreCase = true) && (it.name.contains("baju", ignoreCase = true) || it.name.contains("celana", ignoreCase = true)))) ||
                                        (kid && (it.name.contains("anak", ignoreCase = true) && (it.name.contains("baju", ignoreCase = true) || it.name.contains("celana", ignoreCase = true))))
                            }

                            setRvProduct(finalResult)
                        }

                    }
                    is Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(this@DetailProduct, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setRvProduct(data : List<Product>){
        productAdapter = ProductAdapter(data,object : ProductAdapter.OnClickProduct{
            override fun showBottomNav(data : Product) {
                bottomSheet(data._id)
            }

            override fun productDetail(data : Product){
                startActivity(Intent(this@DetailProduct,DetailProduct::class.java).also{
                    it.putExtra(HomeFragment.HOME_DATA,data._id)
                })
            }

        })

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@DetailProduct,2)
        }
    }

    private fun bottomSheet(id : String){
        val bottomSheet = BottomSheetDialog(this)
        val view = FavoriteBottomSheetBinding.inflate(layoutInflater)
        bottomSheet.apply {
            view.apply {
                setContentView(root)
                textFavorite.setOnClickListener {
                    authPreferencesViewModel.getToken().observe(this@DetailProduct){
                        if(it != null){
                            productViewModel.addWishlist(it,id)
                        }
                    }

                    lifecycleScope.launch {
                        productViewModel.addWishlistResult.collect{ resource ->
                            when (resource) {
                                is Resource.Loading -> {
                                    Toast.makeText(this@DetailProduct, "Loading ...", Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Success -> {
                                    productAdapter.notifyDataSetChanged()
                                    Toast.makeText(this@DetailProduct, "Product successfully added from favorite", Toast.LENGTH_SHORT).show()

                                }
                                is Resource.Error -> {
                                    val errorMessage = resource.message
                                    Toast.makeText(this@DetailProduct, "$errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                show()

            }
        }
    }

    private fun imageViewPager(){

        viewPagerAdapter = ViewPagerAdapter(this, productImage)
        viewPagerAdapter.notifyDataSetChanged()
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {

                productColorAdapter.setSelected(position)
                binding.rvProductColor.smoothScrollToPosition(position)

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewPagerAdapter.notifyDataSetChanged()
    }


    private fun setProductColorRecycler(){
        val count = "${productImage.size} varian warna tersedia."
        binding.rvProductColorCount.text = count
        productColorAdapter = ProductColorAdapter(this@DetailProduct,productImage,object : ProductColorAdapter.ProductImageOnClick{
            override fun productImage(productImage: ProductImage,position : Int) {
                binding.viewPager.setCurrentItem(position,true)
                productColorAdapter.setSelected(position)

            }

            override fun image(imageView: ImageView) {
                makeFlyAnimation(imageView)
            }

        },binding.btnCart)
        binding.rvProductColor.apply {
            adapter = productColorAdapter
            layoutManager = LinearLayoutManager(this@DetailProduct,LinearLayoutManager.HORIZONTAL,false)
        }
        productColorAdapter.setSelected(0)
        productColorAdapter.notifyDataSetChanged()
    }

    private fun setProductSizeRecycler(datas : List<String>,i : Product){
        lifecycleScope.launch {
            detailProductViewModel.productSizeData.collect{ data ->
                val x : MutableList<ProductSize> = mutableListOf()
                    data.mapIndexed { index, productSize ->
                     if(data.size <= datas.size){
                         x.add(ProductSize(productSize.id, datas[index], false))
                     }
                 }
                val count = "${x.distinct().size} ukuran pakaian"
                binding.rvProductSizeCount.text = count

                productSizeAdapter = ProductSizeAdapter(this@DetailProduct,x.distinct(),object : ProductSizeAdapter.ProductSizeOnClick{
                    override fun productSize(productSize: ProductSize, position: Int) {
                        productSizeAdapter.setSelected(position)
                        showBottomSheetBuy(i)
                    }
                })
                productSizeAdapter.setSelected(0)
                productSizeAdapter.notifyDataSetChanged()
                binding.rvProductSize.apply {
                    adapter = productSizeAdapter
                    layoutManager = LinearLayoutManager(this@DetailProduct,LinearLayoutManager.HORIZONTAL,false)
                }


            }
        }

        detailProductViewModel.getAllProductSizeData()
    }

    private fun bottomNavBuy(i : Product){
        val bottomSheetView = BottomSheetBuyBinding.inflate(layoutInflater)
        bottomSheet.apply {
            val view = bottomSheetView.root
            setContentView(view)
            show()
            bottomSheetView.apply {
                tvName.text = i.name
                val price = "Rp${i.price}"
                tvPrice.text = price
                imgClose.setOnClickListener { dismiss() }
                rvColor.adapter = productColorAdapter
                rvColor.layoutManager = GridLayoutManager(this@DetailProduct,5)

                rvSize.adapter = productSizeAdapter
                rvSize.layoutManager = LinearLayoutManager(this@DetailProduct,LinearLayoutManager.HORIZONTAL,false)
            }
        }
    }

    private fun bottomNavDescription(i : Product) {
        binding.continueRead.setOnClickListener {

            val bottomSheetView = BottomSheetDescriptionBinding.inflate(layoutInflater)

            bottomSheet.apply {
                val view = bottomSheetView.root
                setContentView(view)
                val behavior = BottomSheetBehavior.from(view.parent as View)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                val displayMetrics = Resources.getSystem().displayMetrics
                val windowHeight = displayMetrics.heightPixels
                bottomSheetView.parent.minimumHeight = windowHeight
                show()
                bottomSheetView.apply {
                    tvDescription.text = i.description
                    tvNameProduct.text = i.name
                    tvCategory.text = i.category
                    Glide.with(root)
                        .load(i.images.main)
                        .into(imgProduct)
                    if(i.name.contains("lengan pendek",ignoreCase = false)){
                        linearLengan.visibility = View.VISIBLE
                        tvLengan.text = "Lengan Pendek"
                    }else if(i.name.contains("lengan panjang",ignoreCase = false)){
                        linearLengan.visibility = View.VISIBLE
                        tvLengan.text = "Lengan Panjang"
                    }else{
                        linearLengan.visibility = View.GONE
                    }

                    if(i.name.contains("kemeja",ignoreCase = false)){
                        linearIsKemeja.visibility = View.VISIBLE
                    }else{
                        linearIsKemeja.visibility = View.GONE
                    }
                    imgClose.setOnClickListener { dismiss() }
                    linearDescription.setOnClickListener {
                        if (expandableLayout.isExpanded) {
                            expandableLayout.collapse()
                            arrowLinear.setImageResource(R.drawable.ic_arrow_down)
                        } else {
                            expandableLayout.expand()
                            arrowLinear.setImageResource(R.drawable.ic_arrow_up)
                        }
                    }
                }
            }
        }
    }

    private fun setEstimation(){
        val result = "Tiba ${Utils.formatDateRanges(3, "dd", "MMM")}"
        binding.tvEstimation.text = result
    }

    private fun playingBackground(){

        binding.apply {
            linearHeader1.alpha = 0f

            scrollView.viewTreeObserver.addOnScrollChangedListener(
                OnScrollChangedListener {
                    handleHeaderAlpha()
                    handleTextColors()
                    handleScrollPercentage()
                }
            )
        }
    }



    private fun handleHeaderAlpha() {
        val alpha = binding.scrollView.scrollY.toFloat() / 500.0f

        if (alpha >= 0) {
            binding.linearHeader1.alpha = alpha
            updateImageFilters(R.color.grey)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
            }
        }

        if (alpha <= 0.5) {
            binding.linearHeader1.alpha = alpha
            updateImageFilters(R.color.white)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
            };
        }
    }

    private fun updateImageFilters(colorRes: Int) {
        val color = ContextCompat.getColor(this@DetailProduct, colorRes)
        with(binding) {
            imgBack.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            imgSearch.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            imgShare.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            imgCart.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            imgMenu.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun handleTextColors() {
        with(binding) {
            val scrollY = scrollView.scrollY

            if (scrollY >= sectionDescription.top) {
                updateTextColors(R.color.primary_color, R.color.semi_black, R.color.semi_black)
            }


            if (scrollY >= sectionRecommendation.top) {
                updateTextColors(R.color.semi_black, R.color.semi_black, R.color.primary_color)
            }
        }
    }

    private fun updateTextColors(descriptionColor: Int, reviewColor: Int, recommendationColor: Int) {
        with(binding) {
            scrollDescription.setTextColor(ContextCompat.getColor(this@DetailProduct, descriptionColor))
            scrollRecommendation.setTextColor(ContextCompat.getColor(this@DetailProduct, recommendationColor))
        }
    }

    private fun handleScrollPercentage() {
        with(binding) {
            val scrollY = scrollView.scrollY
            val totalScrollRange = scrollView.getChildAt(0).height - scrollView.height
            val scrollPercentage = (scrollY.toFloat() / totalScrollRange) * 100

            if (scrollPercentage >= 10) {
                binding.expandableLayout.expand()
                scrollDescription.setOnClickListener { scrollView.smoothScrollTo(scrollY, sectionDescription.top) }
                scrollRecommendation.setOnClickListener { scrollView.smoothScrollTo(scrollY, sectionRecommendation.top) }
                scrollUp.show()
                scrollUp.setOnClickListener { scrollView.smoothScrollTo(0, 0) }
            } else {
                binding.expandableLayout.collapse()
                scrollUp.hide()
            }
        }
    }

    companion object{
        const val DETAIL = "detail"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}