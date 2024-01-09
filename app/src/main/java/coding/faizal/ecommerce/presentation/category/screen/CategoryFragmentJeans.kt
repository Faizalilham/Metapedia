package coding.faizal.ecommerce.presentation.category.screen

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
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.databinding.FragmentCategoryWomanBinding
import coding.faizal.ecommerce.domain.model.remote.product.Product
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.presentation.home.adapter.ProductAdapter
import coding.faizal.ecommerce.presentation.home.screen.HomeFragment
import coding.faizal.ecommerce.presentation.home.viewmodel.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoryFragmentJeans : Fragment() {

    private var _binding : FragmentCategoryWomanBinding? = null
    private val binding  get() = _binding!!

    private val productViewModel by viewModels<ProductViewModel>()
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
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

    private fun getAllProduct(i : String){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getAllProduct("Bearer $it")
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
                        if(result != null){
                            val finalResult = result.filter { it.name.contains("jeans",ignoreCase = false) && it.name.contains(i,ignoreCase = false) }
                            setRvProduct(finalResult)
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


    private fun setRvProduct(data : List<Product>){
        productAdapter = ProductAdapter(data,object : ProductAdapter.OnClickProduct{
            override fun showBottomNav(data: Product) {
                bottomSheet(data._id)
            }

            override fun productDetail(data : Product){
                startActivity(Intent(requireActivity(), DetailProduct::class.java).also{
                    it.putExtra(HomeFragment.HOME_DATA,data._id)
                })
            }

        })

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireActivity(),2)
        }
    }

    private fun bottomSheet(id : String){
        val bottomSheet = BottomSheetDialog(requireActivity())
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
                                    productAdapter.notifyDataSetChanged()
                                    Toast.makeText(requireActivity(), "Product successfully added from favorite", Toast.LENGTH_SHORT).show()

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}