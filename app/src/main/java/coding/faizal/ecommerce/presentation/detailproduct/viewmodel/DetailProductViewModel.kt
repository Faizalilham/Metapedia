package coding.faizal.ecommerce.presentation.detailproduct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.di.detailproduct.ProvideDetailProductRepositoryImpl
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize
import coding.faizal.ecommerce.domain.usecase.detailproduct.DetailProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailProductViewModel @Inject constructor(
   @ProvideDetailProductRepositoryImpl private val detailProductUseCase : DetailProductUseCase
) : ViewModel() {

    private val _productSizeData = MutableStateFlow<List<ProductSize>>(emptyList())
    val productSizeData : StateFlow<List<ProductSize>> get() = _productSizeData

    fun getAllProductSizeData() {
        viewModelScope.launch {
            _productSizeData.value = detailProductUseCase.getAllSizeProduct()
        }
    }


}