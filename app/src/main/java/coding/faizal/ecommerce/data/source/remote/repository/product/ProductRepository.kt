package coding.faizal.ecommerce.data.source.remote.repository.product

import coding.faizal.ecommerce.domain.model.remote.product.Payment
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.network.product.ApiProductService
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequest
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequestList
import coding.faizal.ecommerce.data.source.remote.response.product.PaymentRequest
import coding.faizal.ecommerce.data.source.remote.response.product.WishlistRequest
import coding.faizal.ecommerce.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.domain.model.remote.product.Product
import coding.faizal.ecommerce.domain.model.remote.product.ProductOrder
import coding.faizal.ecommerce.domain.repository.product.IProductRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper.mapFromListProductResponseToEntities
import coding.faizal.ecommerce.utils.DataMapper.mapFromListWishlistResponseToEntities
import coding.faizal.ecommerce.utils.DataMapper.mapFromProductOrderResponseToEntities
import coding.faizal.ecommerce.utils.DataMapper.mapFromProductPaymentResponseToEntities
import coding.faizal.ecommerce.utils.DataMapper.mapFromSingleProductResponseToEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepository @Inject constructor(
    private val apiProductService: ApiProductService
) : IProductRepository {
    override fun getAllProduct(token: String): Flow<Resource<List<Product>>> = flow {
        val errorHandling = ErrorHandling<List<Product>>(this)
        try {
            val response = apiProductService.getAllProduct(token)

            if (response.code == 200) {
                val result = response.data
                emit(Resource.Success(mapFromListProductResponseToEntities(result.products),response.status))
            } else {
                errorHandling.handleError(response.code.toString(), response.status)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun getProductById(token: String, id: String): Flow<Resource<Product>> = flow {
        val errorHandling = ErrorHandling<Product>(this)
        try {
            val response = apiProductService.getProductById(token,id)

            if (response.code == 200) {
                val result = response.data
                emit(Resource.Success(mapFromSingleProductResponseToEntities(result.product),response.status))
            } else {
                errorHandling.handleError(response.code.toString(), response.status)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllWishlist(token: String): Flow<Resource<ListWishlist>> = flow {
        val errorHandling = ErrorHandling<ListWishlist>(this)
        try {
            val response = apiProductService.getAllWishlist(token)

            if (response.success) {
                val result = response.data
                emit(Resource.Success(mapFromListWishlistResponseToEntities(result.wishlist),response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun addWishlist(token: String, id: String): Flow<Resource<ListWishlist>> = flow {
        val errorHandling = ErrorHandling<ListWishlist>(this)
        try {
            val response = apiProductService.addWishlist(token, WishlistRequest(id))

            if (response.success) {
                val result = response.data
                emit(Resource.Success(mapFromListWishlistResponseToEntities(result.wishlist),response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteWishlist(token: String, id: String): Flow<Resource<ListWishlist>> = flow {
        val errorHandling = ErrorHandling<ListWishlist>(this)
        try {
            val response = apiProductService.deleteWishlist(token,id)

            if (response.success) {
                val result = response.data
                emit(Resource.Success(mapFromListWishlistResponseToEntities(result.wishlist),response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun orderProduct(
        token: String,
        product: String,
        variant: String,
        quantity: Int,
        price: String
    ): Flow<Resource<ProductOrder>> = flow {
        val errorHandling = ErrorHandling<ProductOrder>(this)
        try {
            val response = apiProductService.doOrder(token,
                OrderItemRequestList(listOf(OrderItemRequest(product,variant,quantity,price)))
            )

            if (response.status == "success") {
                val result = response.order
                emit(Resource.Success(mapFromProductOrderResponseToEntities(result),response.status))
            } else {
                errorHandling.handleError(response.status, response.status)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun paymentProduct(token: String, orderId: String): Flow<Resource<Payment>> = flow {
            val errorHandling = ErrorHandling<Payment>(this)
            try {
                val response = apiProductService.doPayment(token, PaymentRequest(orderId))

                if (response.success) {
                    emit(Resource.Success(mapFromProductPaymentResponseToEntities(response),response.status))
                } else {
                    errorHandling.handleError(response.status, response.status)
                }
            } catch (e: HttpException) {
                errorHandling.handleHttpException(e)
            }
        }.flowOn(Dispatchers.IO)
}
