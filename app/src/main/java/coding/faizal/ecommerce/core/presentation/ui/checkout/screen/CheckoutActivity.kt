package coding.faizal.ecommerce.core.presentation.ui.checkout.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.presentation.ui.checkout.adapter.CheckoutAdapter
import coding.faizal.ecommerce.core.presentation.ui.checkout.utils.MidtransConfig
import coding.faizal.ecommerce.core.presentation.ui.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.core.presentation.viewmodel.product.ProductViewModel
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequest
import coding.faizal.ecommerce.databinding.ActivityCheckoutBinding
import com.midtrans.sdk.corekit.core.MidtransSDK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {
    private var _binding : ActivityCheckoutBinding? = null
    private val binding get() = _binding!!

    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val productViewModel by viewModels<ProductViewModel>()

    private lateinit var checkoutAdapter : CheckoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MidtransConfig.setupMidTrans(this)

        val i = intent.getParcelableExtra<OrderItemRequest>(DetailProduct.DETAIL)
        if(i != null){
            getProductById(i.product,i)
        }
        binding.btnCheckout.setOnClickListener {
            if(i != null){
                doOrder(i)

            }
        }
    }

    private fun getProductById(id : String,i : OrderItemRequest){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.getProduct("Bearer $it",id)
            }
        }

        lifecycleScope.launch {
            productViewModel.productResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if(result != null){
                           setupView(i,result.name)
                        }
                    }
                    is Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(this@CheckoutActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupView(i : OrderItemRequest,name : String){
        binding.apply {
            tvPrice.text = i.price
            tvTotalPrice.text = i.price
            setRv(listOf(i),name)
        }
    }

    private fun setRv(data : List<OrderItemRequest>,name : String){
        checkoutAdapter = CheckoutAdapter(data,name)
        binding.rvProduct.apply {
            adapter = checkoutAdapter
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun doOrder(orderItemRequest: OrderItemRequest){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                orderItemRequest.apply {
                    productViewModel.doOrder("Bearer $it",product, variant, quantity, price)
                }
            }
        }

        lifecycleScope.launch {
            productViewModel.productOrderResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loadingPanel.visibility = View.VISIBLE

                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if(result != null){

                            doPayment(result.id)
                        }
                    }
                    is Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(this@CheckoutActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun doPayment(orderId : String){
        authPreferencesViewModel.getToken().observe(this){
            if(it != null){
                productViewModel.doPayment("Bearer $it",orderId)
            }
        }

        lifecycleScope.launch {
            productViewModel.productPaymentResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loadingPanel.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val result = resource.data
                        if(result != null){
                            getSnapToken(result.token)
                            binding.loadingPanel.visibility = View.GONE
                        }
                    }
                    is Resource.Error -> {

                        val errorMessage = resource.message
                        Toast.makeText(this@CheckoutActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun getSnapToken(snap : String){
        MidtransSDK.getInstance().transactionRequest = MidtransConfig.initTransactionRequest()
        MidtransSDK.getInstance().startPaymentUiFlow(this,snap)
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}