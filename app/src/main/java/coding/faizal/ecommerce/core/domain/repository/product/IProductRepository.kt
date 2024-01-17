package coding.faizal.ecommerce.core.domain.repository.product



import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.product.ProductSize
import coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.core.domain.model.remote.product.Payment
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getAllSizeProduct(token : String) : Flow<List<ProductSize>>

    fun getAllProduct(token : String) : Flow<Resource<List<Product>>>

    fun getProductById(token : String,id : String) : Flow<Resource<Product>>

    fun getAllWishlist(token : String) : Flow<Resource<ListWishlist>>

    fun addWishlist(token : String,id : String) : Flow<Resource<ListWishlist>>

    fun deleteWishlist(token : String,id : String) : Flow<Resource<ListWishlist>>

    fun orderProduct(token : String, product : String,variant : String, quantity : Int,price : String) : Flow<Resource<ProductOrder>>

    fun paymentProduct(token :String,orderId : String) : Flow<Resource<Payment>>


}