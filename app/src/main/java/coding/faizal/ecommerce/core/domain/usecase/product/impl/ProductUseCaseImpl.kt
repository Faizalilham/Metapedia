package coding.faizal.ecommerce.core.domain.usecase.product.impl


import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.product.ProductSize
import coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.domain.repository.product.IProductRepository
import coding.faizal.ecommerce.core.domain.usecase.product.ProductUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCaseImpl @Inject constructor(private val IProductRepository : IProductRepository) :
    ProductUseCase {
    override fun getAllSizeProduct(token : String): Flow<List<ProductSize>> = IProductRepository.getAllSizeProduct(token)

    override fun getAllProduct(token: String): Flow<Resource<List<Product>>> = IProductRepository.getAllProduct(token)

    override fun getProductById(token: String, id: String): Flow<Resource<Product>> = IProductRepository.getProductById(token,id)

    override fun getAllWishlist(token: String): Flow<Resource<ListWishlist>> = IProductRepository.getAllWishlist(token)

    override fun addWishlist(token: String, id: String): Flow<Resource<ListWishlist>> = IProductRepository.addWishlist(token,id)

    override fun deleteWishlist(token: String, id: String): Flow<Resource<ListWishlist>> = IProductRepository.deleteWishlist(token,id)
    override fun orderProduct(
        token: String,
        product: String,
        variant: String,
        quantity: Int,
        price: String
    ): Flow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder>> {
        return IProductRepository.orderProduct(token, product, variant, quantity, price)
    }

    override fun paymentProduct(token: String, orderId: String): Flow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.product.Payment>> {
        return IProductRepository.paymentProduct(token, orderId)
    }
}