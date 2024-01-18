package coding.faizal.ecommerce.core.domain.usecase.product

import coding.faizal.ecommerce.core.domain.model.remote.product.Payment
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.product.ProductSize
import coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {

    fun getAllSizeProduct() : Flow<List<ProductSize>>

    fun getAllProduct() : Flow<Resource<List<Product>>>

    fun getProductById(id : String) : Flow<Resource<Product>>

    fun getAllWishlist() : Flow<Resource<ListWishlist>>

    fun addWishlist(id : String) : Flow<Resource<ListWishlist>>

    fun deleteWishlist(id : String) : Flow<Resource<ListWishlist>>

    fun orderProduct( product : String,variant : String, quantity : Int,price : String) : Flow<Resource<ProductOrder>>

    fun paymentProduct(orderId : String) : Flow<Resource<Payment>>
}