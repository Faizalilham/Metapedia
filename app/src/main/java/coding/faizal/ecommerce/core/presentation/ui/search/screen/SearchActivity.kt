package coding.faizal.ecommerce.core.presentation.ui.search.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.databinding.ActivitySearchBinding
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.presentation.viewmodel.authentication.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.ui.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.presentation.ui.home.adapter.ProductAdapter
import coding.faizal.ecommerce.presentation.ui.home.screen.HomeFragment
import coding.faizal.ecommerce.presentation.viewmodel.product.ProductViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHelp
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private var _binding : ActivitySearchBinding? = null
    private val binding get() = _binding!!

    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val productViewModel by viewModels<ProductViewModel>()
    private lateinit var productAdapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSearch.requestFocus()

        binding.linearTips.setOnClickListener {navigateToHelp(this) }
        binding.imgBack.setOnClickListener {  finish() }
        binding.imgBacks.setOnClickListener { finish() }
        val i = intent.getStringExtra(HomeFragment.HOME_DATA)
        doSearch()
        if(i != null){
            setupVisibility(true)
            getAllProduct(null)

        }else{
            doSearch()
        }


    }

    private fun doSearch(){
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Handle aksi pencarian di sini
                getAllProduct(binding.etSearch.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }


    }

    private fun getAllProduct(q : String?){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getAllProduct("Bearer $it")
            }
        }

        lifecycleScope.launch {
            productViewModel.listProductResult.collect{ resource ->
                when (resource) {
                    is coding.faizal.ecommerce.core.data.Resource.Loading -> {

                        setRvProduct(listOf())
                    }
                    is coding.faizal.ecommerce.core.data.Resource.Success -> {
                        val result = resource.data
                        if(result != null){
                            if(q != null){
                                val search = result.filter { it.name.contains(q) }
                                setRvProduct(search)

                            }else{
                                setRvProduct(result)
                            }
                        }
                    }
                    is coding.faizal.ecommerce.core.data.Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(this@SearchActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setRvProduct(data : List<coding.faizal.ecommerce.core.domain.model.remote.product.Product>){
        productAdapter = ProductAdapter(data,object : ProductAdapter.OnClickProduct{
            override fun showBottomNav(data : coding.faizal.ecommerce.core.domain.model.remote.product.Product) {
                bottomSheet(data._id)
            }

            override fun productDetail(data : coding.faizal.ecommerce.core.domain.model.remote.product.Product){
                startActivity(Intent(this@SearchActivity, DetailProduct::class.java).also{
                    it.putExtra(HomeFragment.HOME_DATA,data._id)
                })
            }

        })

        binding.rv.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@SearchActivity,2)

        }
    }

    private fun bottomSheet(id : String){
        val bottomSheet = BottomSheetDialog(this)
        val view = FavoriteBottomSheetBinding.inflate(layoutInflater)
        bottomSheet.apply {
            view.apply {
                setContentView(root)
                textFavorite.setOnClickListener {
                    authPreferencesViewModel.getToken().observe(this@SearchActivity){
                        if(it != null){
                            productViewModel.addWishlist("Bearer $it",id)
                        }
                    }

                    lifecycleScope.launch {
                        productViewModel.addWishlistResult.collect{ resource ->
                            when (resource) {
                                is coding.faizal.ecommerce.core.data.Resource.Loading -> {
                                    Toast.makeText(this@SearchActivity, "Loading ...", Toast.LENGTH_SHORT).show()
                                }
                                is coding.faizal.ecommerce.core.data.Resource.Success -> {

                                    Toast.makeText(this@SearchActivity, "Product successfully added to favorite", Toast.LENGTH_SHORT).show()

                                }
                                is coding.faizal.ecommerce.core.data.Resource.Error -> {

                                    val errorMessage = resource.message
                                    Toast.makeText(this@SearchActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                show()

            }
        }
    }

    private fun setupVisibility(isVisible : Boolean){
        binding.apply {
            if(isVisible){
                linearTips.visibility = View.VISIBLE
                header.visibility = View.VISIBLE
                rv.visibility = View.VISIBLE
                linearMaybeYouLike.visibility = View.GONE
            }else{
                linearTips.visibility = View.GONE
                header.visibility = View.GONE
                linearMaybeYouLike.visibility = View.VISIBLE
                rv.visibility = View.VISIBLE
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}