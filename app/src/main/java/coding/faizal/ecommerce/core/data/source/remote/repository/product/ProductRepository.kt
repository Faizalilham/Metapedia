package coding.faizal.ecommerce.core.data.source.remote.repository.product

import android.util.Log
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequest
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequestList
import coding.faizal.ecommerce.data.source.remote.response.product.WishlistRequest
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.data.source.remote.network.product.ApiProductService
import coding.faizal.ecommerce.core.data.source.remote.response.product.payment.PaymentRequest
import coding.faizal.ecommerce.core.domain.model.local.product.ProductSize
import coding.faizal.ecommerce.core.domain.model.remote.product.ListWishlist
import coding.faizal.ecommerce.core.domain.model.remote.product.Payment
import coding.faizal.ecommerce.core.domain.model.remote.product.Product
import coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder
import coding.faizal.ecommerce.core.domain.repository.product.IProductRepository
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
    override fun getAllSizeProduct(): Flow<List<ProductSize>>  = flow{
        try {
            val response = apiProductService.getAllProduct()
            val listProductSize = response.data.products
                .flatMap { pr ->
                    pr.variants.mapIndexed { index, pv ->
                        ProductSize(
                            index + 1,
                            pv.size,
                            false
                        )
                    }
                }
            emit(listProductSize.toList())
        } catch (e: HttpException) {
            Log.d("ERROR","${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllProduct(): Flow<Resource<List<Product>>> = flow {
        val errorHandling = ErrorHandling<List<Product>>(this)
        try {
            val response = apiProductService.getAllProduct()

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

    override fun getProductById(id: String): Flow<Resource<Product>> = flow {
        val errorHandling = ErrorHandling<Product>(this)
        try {
            val response = apiProductService.getProductById(id)

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

    override fun getAllWishlist(): Flow<Resource<ListWishlist>> = flow {
        val errorHandling = ErrorHandling<ListWishlist>(this)
        try {
            val response = apiProductService.getAllWishlist()

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

    override fun addWishlist(id: String): Flow<Resource<ListWishlist>> = flow {
        val errorHandling = ErrorHandling<ListWishlist>(this)
        try {
            val response = apiProductService.addWishlist(WishlistRequest(id))

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

    override fun deleteWishlist(id: String): Flow<Resource<ListWishlist>> = flow {
        val errorHandling = ErrorHandling<ListWishlist>(this)
        try {
            val response = apiProductService.deleteWishlist(id)

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
        product: String,
        variant: String,
        quantity: Int,
        price: String
    ): Flow<Resource<ProductOrder>> = flow {
        val errorHandling = ErrorHandling<ProductOrder>(this)
        try {
            val response = apiProductService.doOrder(
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

    override fun paymentProduct(orderId: String): Flow<Resource<Payment>> = flow {
            val errorHandling = ErrorHandling<Payment>(this)
            try {
                val response = apiProductService.doPayment(PaymentRequest(orderId))

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
