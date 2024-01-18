package coding.faizal.ecommerce.core.domain.usecase.product.impl


import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.product.ProductSize
import coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.core.domain.model.remote.product.Payment
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder
import coding.faizal.ecommerce.core.domain.repository.product.IProductRepository
import coding.faizal.ecommerce.core.domain.usecase.product.ProductUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCaseImpl @Inject constructor(private val IProductRepository : IProductRepository) :
    ProductUseCase {
    override fun getAllSizeProduct(): Flow<List<ProductSize>> = IProductRepository.getAllSizeProduct()

    override fun getAllProduct(): Flow<Resource<List<Product>>> = IProductRepository.getAllProduct()

    override fun getProductById(id: String): Flow<Resource<Product>> = IProductRepository.getProductById(id)

    override fun getAllWishlist(): Flow<Resource<ListWishlist>> = IProductRepository.getAllWishlist()

    override fun addWishlist(id: String): Flow<Resource<ListWishlist>> = IProductRepository.addWishlist(id)

    override fun deleteWishlist(id: String): Flow<Resource<ListWishlist>> = IProductRepository.deleteWishlist(id)
    override fun orderProduct(
        product: String,
        variant: String,
        quantity: Int,
        price: String
    ): Flow<Resource<ProductOrder>> {
        return IProductRepository.orderProduct(product, variant, quantity, price)
    }

    override fun paymentProduct(orderId: String): Flow<Resource<Payment>> {
        return IProductRepository.paymentProduct(orderId)
    }
}