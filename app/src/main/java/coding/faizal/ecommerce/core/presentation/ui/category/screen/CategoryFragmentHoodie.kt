package coding.faizal.ecommerce.core.presentation.ui.category.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.databinding.FragmentCategoryWomanBinding
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.presentation.ui.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.core.presentation.ui.home.adapter.ProductAdapter
import coding.faizal.ecommerce.core.presentation.ui.home.screen.HomeFragment
import coding.faizal.ecommerce.core.presentation.viewmodel.product.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoryFragmentHoodie : Fragment() {

    private var _binding : FragmentCategoryWomanBinding? = null
    private val binding  get() = _binding!!

    private val productViewModel by viewModels<ProductViewModel>()
    private lateinit var productAdapter : ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryWomanBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllProduct("")
    }

    private fun getAllProduct(i: String) {
        productViewModel.getAllProduct()

        lifecycleScope.launch {
            productViewModel.listProductResult.collect { resource ->
                processResource(resource, i)
            }
        }
    }


    private fun List<Product>.filterProductsByKeywordAndName(name: String): List<Product> {
        return filter { it.name.contains("hoodie", ignoreCase = false) && it.name.contains(name, ignoreCase = false) }
    }

    private fun processResource(resource: Resource<List<Product>>, keyword: String) {
        when (resource) {
            is Resource.Loading -> {
                setRvProduct(listOf())
            }
            is Resource.Success -> {
                val result = resource.data
                if (result != null) {
                    val finalResult = result.filterProductsByKeywordAndName(keyword)
                    setRvProduct(finalResult)
                }
            }
            is Resource.Error -> {
                val errorMessage = resource.message
                Toast.makeText(requireActivity(), "$errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createProductAdapter(data: List<Product>): ProductAdapter {
        return ProductAdapter(data, object : ProductAdapter.OnClickProduct {
            override fun showBottomNav(data: Product) {
                showBottomSheet(data._id)
            }

            override fun productDetail(data: Product) {
                startActivity(Intent(requireActivity(), DetailProduct::class.java).also {
                    it.putExtra(HomeFragment.HOME_DATA, data._id)
                })
            }
        })
    }

    private fun setRvProduct(data: List<Product>) {
        productAdapter = createProductAdapter(data)
        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }


    private fun showBottomSheet(id: String) {
        val bottomSheet = BottomSheetDialog(requireActivity())
        val view = FavoriteBottomSheetBinding.inflate(layoutInflater)

        bottomSheet.apply {
            view.apply {
                setContentView(root)
                textFavorite.setOnClickListener {
                    productViewModel.addWishlist(id)

                    lifecycleScope.launch {
                        productViewModel.addWishlistResult.collect { resource ->
                            handleWishlistResult(resource)
                        }
                    }
                }
                show()
            }
        }
    }

    private fun handleWishlistResult(resource: Resource<*>) {
        when (resource) {
            is Resource.Loading -> {
                Toast.makeText(requireActivity(), "Loading ...", Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                productAdapter.notifyDataSetChanged()
                Toast.makeText(requireActivity(), "Product successfully added from favorite", Toast.LENGTH_SHORT).show()
            }
            is Resource.Error -> {
                val errorMessage = resource.message
                Toast.makeText(requireActivity(), "$errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}