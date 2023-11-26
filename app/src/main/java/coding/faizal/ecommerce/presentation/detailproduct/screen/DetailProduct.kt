package coding.faizal.ecommerce.presentation.detailproduct.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ActivityDetailProductBinding
import coding.faizal.ecommerce.databinding.BottomSheetDescriptionBinding
import coding.faizal.ecommerce.databinding.BottomSheetHelpBinding
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductImage
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize
import coding.faizal.ecommerce.presentation.detailproduct.adapter.ProductColorAdapter
import coding.faizal.ecommerce.presentation.detailproduct.adapter.ProductSizeAdapter
import coding.faizal.ecommerce.presentation.detailproduct.adapter.ViewPagerAdapter
import coding.faizal.ecommerce.presentation.detailproduct.viewmodel.DetailProductViewModel
import coding.faizal.ecommerce.presentation.forgetpassword.screen.ForgetPasswordActivity
import coding.faizal.ecommerce.presentation.help.adapter.HelpAdapter
import coding.faizal.ecommerce.presentation.help.screen.HelpActivity
import coding.faizal.ecommerce.presentation.home.utils.Utils
import coding.faizal.ecommerce.utils.UiUtil.toggleShow
import coding.faizal.ecommerce.utils.spanText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailProduct : AppCompatActivity() {

    private var _binding : ActivityDetailProductBinding? = null
    private val binding get() = _binding!!

    private val detailProductViewModel by viewModels<DetailProductViewModel>()

    private lateinit var productSizeAdapter: ProductSizeAdapter
    private lateinit var productColorAdapter: ProductColorAdapter

    private var productImage = listOf<ProductImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productImage = listOf(
            ProductImage(1,"BLACK","https://res.cloudinary.com/dmhpacb7m/image/upload/v1699357835/ezikq0rbqddsokbstahl.png",false),
            ProductImage(2,"BLUE","https://res.cloudinary.com/dmhpacb7m/image/upload/v1699357835/ezikq0rbqddsokbstahl.png",false),

            )


        binding.scrollUp.hide()
        playingBackground()
        setEstimation()
        bottomNavDescription()
        setProductSizeRecycler()
        imageViewPager()
        setProductColorRecycler()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
        }


    }

    private fun imageViewPager(){

        val viewPagerAdapter = ViewPagerAdapter(this, productImage)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {

                binding.rvProductColor.smoothScrollToPosition(position)
                productColorAdapter.setSelected(position)

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    private fun setProductColorRecycler(){
        productColorAdapter = ProductColorAdapter(productImage,object : ProductColorAdapter.ProductImageOnClick{
            override fun productImage(productImage: ProductImage,position : Int) {
                binding.viewPager.setCurrentItem(position,true)
            }

        })
        binding.rvProductColor.apply {
            adapter = productColorAdapter
            layoutManager = LinearLayoutManager(this@DetailProduct,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setProductSizeRecycler(){
        lifecycleScope.launch {
            detailProductViewModel.productSizeData.collect{ data ->
                productSizeAdapter = ProductSizeAdapter(data,object : ProductSizeAdapter.ProductSizeOnClick{
                    override fun productSize(productSize: ProductSize, position: Int) {
                        TODO("Not yet implemented")
                    }
                })
                binding.rvProductSize.apply {
                    adapter = productSizeAdapter
                    layoutManager = LinearLayoutManager(this@DetailProduct,LinearLayoutManager.HORIZONTAL,false)
                }


            }
        }
        detailProductViewModel.getAllProductSizeData()
    }

    private fun bottomNavDescription() {
        val bottomSheet = BottomSheetDialog(this)
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
        }
    }

    private fun handleTextColors() {
        with(binding) {
            val scrollY = scrollView.scrollY

            if (scrollY >= sectionDescription.top) {
                updateTextColors(R.color.primary_color, R.color.semi_black, R.color.semi_black)
            }

            if (scrollY >= sectionReview.top) {
                updateTextColors(R.color.semi_black, R.color.primary_color, R.color.semi_black)
            }

            if (scrollY >= sectionRecommendation.top) {
                updateTextColors(R.color.semi_black, R.color.semi_black, R.color.primary_color)
            }
        }
    }

    private fun updateTextColors(descriptionColor: Int, reviewColor: Int, recommendationColor: Int) {
        with(binding) {
            scrollDescription.setTextColor(ContextCompat.getColor(this@DetailProduct, descriptionColor))
            scrollReview.setTextColor(ContextCompat.getColor(this@DetailProduct, reviewColor))
            scrollRecommendation.setTextColor(ContextCompat.getColor(this@DetailProduct, recommendationColor))
        }
    }

    private fun handleScrollPercentage() {
        with(binding) {
            val scrollY = scrollView.scrollY
            val totalScrollRange = scrollView.getChildAt(0).height - scrollView.height
            val scrollPercentage = (scrollY.toFloat() / totalScrollRange) * 100

            if (scrollPercentage >= 30) {
                toggleShow(true, header, linearTextScroll)
                scrollDescription.setOnClickListener { scrollView.smoothScrollTo(scrollY, sectionDescription.top) }
                scrollReview.setOnClickListener { scrollView.smoothScrollTo(scrollY, sectionReview.top) }
                scrollRecommendation.setOnClickListener { scrollView.smoothScrollTo(scrollY, sectionRecommendation.top) }
                scrollUp.show()
                scrollUp.setOnClickListener { scrollView.smoothScrollTo(0, 0) }
            } else {
                toggleShow(false, header, linearTextScroll)
                scrollUp.hide()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}