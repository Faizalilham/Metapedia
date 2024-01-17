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
import coding.faizal.ecommerce.domain.usecase.product.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    @ProvideProductRepositoryImpl private val productUseCase: ProductUseCase) : ViewModel(){

    private val _listProductResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<List<coding.faizal.ecommerce.core.domain.model.remote.product.Product>>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val listProductResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<List<coding.faizal.ecommerce.core.domain.model.remote.product.Product>>> = _listProductResult

    private val _productResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.Product>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val productResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.Product>> = _productResult

    private val _listWishlistResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val listWishlistResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist>> = _listWishlistResult

    private val _addWishlistResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val addWishlistResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist>> = _addWishlistResult

    private val _removeWishlistResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val removeWishlistResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist>> = _removeWishlistResult

    private val _productOrderResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val productOrderResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder>> = _productOrderResult

    private val _productPaymentResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.Payment>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val productPaymentResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.Payment>> = _productPaymentResult

    private val _productSizeResult = MutableStateFlow<List<coding.faizal.ecommerce.core.domain.model.local.product.ProductSize>>(listOf())
    val productSizeResult : StateFlow<List<coding.faizal.ecommerce.core.domain.model.local.product.ProductSize>> = _productSizeResult


    fun getAllProduct(token : String){
        viewModelScope.launch {
            try {
                _listProductResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.getAllProduct(token).asLiveData().asFlow().first()
                _listProductResult.value = result
            } catch (e: Exception) {
                _listProductResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun getProduct(token : String,id : String){
        viewModelScope.launch {
            try {
                _productResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.getProductById(token,id).asLiveData().asFlow().first()
                _productResult.value = result
            } catch (e: Exception) {
                _productResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun getAllWishlist(token : String){
        viewModelScope.launch {
            try {
                _listWishlistResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.getAllWishlist(token).asLiveData().asFlow().first()
                _listWishlistResult.value = result
            } catch (e: Exception) {
                _listWishlistResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun addWishlist(token : String,id : String){
        viewModelScope.launch {
            try {
                _addWishlistResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.addWishlist(token,id).asLiveData().asFlow().first()
                _addWishlistResult.value = result
            } catch (e: Exception) {
                _addWishlistResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun remove(token : String,id : String){
        viewModelScope.launch {
            try {
                _removeWishlistResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.deleteWishlist(token,id).asLiveData().asFlow().first()
                _removeWishlistResult.value = result
            } catch (e: Exception) {
                _removeWishlistResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun doOrder(token : String,product : String,variant : String,quantity : Int,price : String){
        viewModelScope.launch {
            try {
                _productOrderResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.orderProduct(token,product, variant, quantity, price).asLiveData().asFlow().first()
                _productOrderResult.value = result
            } catch (e: Exception) {
                _productOrderResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun doPayment(token : String,orderId : String){
        viewModelScope.launch {
            try {
                _productPaymentResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = productUseCase.paymentProduct(token,orderId).asLiveData().asFlow().first()
                _productPaymentResult.value = result
            } catch (e: Exception) {
                _productPaymentResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun getProductSize(token : String){
        viewModelScope.launch {
            try {
                val result = productUseCase.getAllSizeProduct(token).asLiveData().asFlow().first()
                _productSizeResult.value = result
            } catch (e: Exception) {
                Log.d("ERROR","${e.message}")
                _productSizeResult.value = listOf()
            }
        }
    }

}