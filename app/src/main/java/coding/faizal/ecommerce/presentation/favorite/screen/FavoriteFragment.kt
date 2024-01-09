package coding.faizal.ecommerce.presentation.favorite.screen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.FavoriteBottomSheetBinding
import coding.faizal.ecommerce.databinding.FragmentFavoriteBinding
import coding.faizal.ecommerce.domain.model.remote.product.Product
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.presentation.home.adapter.ProductAdapter
import coding.faizal.ecommerce.presentation.home.screen.HomeFragment
import coding.faizal.ecommerce.presentation.home.viewmodel.ProductViewModel
import coding.faizal.ecommerce.utils.NavigationUtils
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToCart
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {


    private lateinit var binding : FragmentFavoriteBinding

    private val productViewModel by viewModels<ProductViewModel>()
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private lateinit var productAdapter : ProductAdapter
    private val listProduct : MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        getAllFavorite()
    }

    private fun navigation(){

        binding.imgCart.setOnClickListener { navigateToCart(requireActivity()) }
        binding.imgMenu.setOnClickListener { NavigationUtils.navigateToMenu(requireActivity()) }
    }

    private fun getAllFavorite(){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getAllWishlist("Bearer $it")
            }
        }

        lifecycleScope.launch {
            productViewModel.listWishlistResult.collect{ resource ->
                when(resource){
                    is Resource.Loading -> {
                        setRvFavorite(listOf())
                    }

                    is Resource.Success -> {

                        val result = resource.data
                        result?.wishlist?.forEach {
                            getProductById(it.product)
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

    private fun getProductById(id : String){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getProduct("Bearer $it",id)
            }
        }

        lifecycleScope.launch {
            productViewModel.productResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        setRvFavorite(listOf())
                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if(result != null){
                            listProduct.add(result)
                            setRvFavorite(listProduct)
                            productAdapter.notifyDataSetChanged()
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



    private fun setRvFavorite(result : List<Product>){
        productAdapter = ProductAdapter(result.distinct(),object : ProductAdapter.OnClickProduct{
            override fun showBottomNav(data : Product) {
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
        val bottomSheet = BottomSheetDialog(requireContext())
        val view = FavoriteBottomSheetBinding.inflate(layoutInflater)
        bottomSheet.apply {
            view.apply {
                setContentView(root)
                textFavorite.text = "Hapus produk dari wishlist"
                textFavorite.setOnClickListener {
                    authPreferencesViewModel.getToken().observe(requireActivity()){
                        if(it != null){
                            productViewModel.remove("Bearer $it",id)
                        }
                    }

                    lifecycleScope.launch {
                        productViewModel.removeWishlistResult.collect{ resource ->
                            when (resource) {
                                is Resource.Loading -> {
                                    Toast.makeText(requireActivity(), "Loading ...", Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Success -> {
                                    productAdapter.notifyDataSetChanged()
                                    Toast.makeText(requireActivity(), "Product successfully deleted from favorite", Toast.LENGTH_SHORT).show()

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


}