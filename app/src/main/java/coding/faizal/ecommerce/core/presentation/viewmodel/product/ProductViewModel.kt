package coding.faizal.ecommerce.core.presentation.viewmodel.product

import android.util.Log
import coding.faizal.ecommerce.core.domain.model.remote.product.Payment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.di.product.ProvideProductRepositoryImpl
import coding.faizal.ecommerce.core.domain.model.local.product.ProductSize
import coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder
import coding.faizal.ecommerce.core.domain.usecase.product.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    @ProvideProductRepositoryImpl private val productUseCase: ProductUseCase
) : ViewModel(){

    private val _listProductResult = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val listProductResult : StateFlow<Resource<List<Product>>> = _listProductResult

    private val _productResult = MutableStateFlow<Resource<Product>>(Resource.Loading())
    val productResult : StateFlow<Resource<Product>> = _productResult

    private val _listWishlistResult = MutableStateFlow<Resource<ListWishlist>>(Resource.Loading())
    val listWishlistResult : StateFlow<Resource<ListWishlist>> = _listWishlistResult

    private val _addWishlistResult = MutableStateFlow<Resource<ListWishlist>>(Resource.Loading())
    val addWishlistResult : StateFlow<Resource<ListWishlist>> = _addWishlistResult

    private val _removeWishlistResult = MutableStateFlow<Resource<ListWishlist>>(Resource.Loading())
    val removeWishlistResult : StateFlow<Resource<ListWishlist>> = _removeWishlistResult

    private val _productOrderResult = MutableStateFlow<Resource<ProductOrder>>(Resource.Loading())
    val productOrderResult : StateFlow<Resource<ProductOrder>> = _productOrderResult

    private val _productPaymentResult = MutableStateFlow<Resource<Payment>>(Resource.Loading())
    val productPaymentResult : StateFlow<Resource<Payment>> = _productPaymentResult

    private val _productSizeResult = MutableStateFlow<List<ProductSize>>(listOf())
    val productSizeResult : StateFlow<List<ProductSize>> = _productSizeResult


    fun getAllProduct(){
        viewModelScope.launch {
            try {
                _listProductResult.value = Resource.Loading()
                val result = productUseCase.getAllProduct().asLiveData().asFlow().first()
                _listProductResult.value = result
            } catch (e: Exception) {
                _listProductResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun getProduct(id : String){
        viewModelScope.launch {
            try {
                _productResult.value = Resource.Loading()
                val result = productUseCase.getProductById(id).asLiveData().asFlow().first()
                _productResult.value = result
            } catch (e: Exception) {
                _productResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun getAllWishlist(){
        viewModelScope.launch {
            try {
                _listWishlistResult.value = Resource.Loading()
                val result = productUseCase.getAllWishlist().asLiveData().asFlow().first()
                _listWishlistResult.value = result
            } catch (e: Exception) {
                _listWishlistResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun addWishlist(id : String){
        viewModelScope.launch {
            try {
                _addWishlistResult.value = Resource.Loading()
                val result = productUseCase.addWishlist(id).asLiveData().asFlow().first()
                _addWishlistResult.value = result
            } catch (e: Exception) {
                _addWishlistResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun remove(id : String){
        viewModelScope.launch {
            try {
                _removeWishlistResult.value = Resource.Loading()
                val result = productUseCase.deleteWishlist(id).asLiveData().asFlow().first()
                _removeWishlistResult.value = result
            } catch (e: Exception) {
                _removeWishlistResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun doOrder(product : String,variant : String,quantity : Int,price : String){
        viewModelScope.launch {
            try {
                _productOrderResult.value = Resource.Loading()
                val result = productUseCase.orderProduct(product, variant, quantity, price).asLiveData().asFlow().first()
                _productOrderResult.value = result
            } catch (e: Exception) {
                _productOrderResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun doPayment(orderId : String){
        viewModelScope.launch {
            try {
                _productPaymentResult.value = Resource.Loading()
                val result = productUseCase.paymentProduct(orderId).asLiveData().asFlow().first()
                _productPaymentResult.value = result
            } catch (e: Exception) {
                _productPaymentResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun getProductSize(){
        viewModelScope.launch {
            try {
                val result = productUseCase.getAllSizeProduct().asLiveData().asFlow().first()
                _productSizeResult.value = result
            } catch (e: Exception) {
                Log.d("ERROR","${e.message}")
                _productSizeResult.value = listOf()
            }
        }
    }

}