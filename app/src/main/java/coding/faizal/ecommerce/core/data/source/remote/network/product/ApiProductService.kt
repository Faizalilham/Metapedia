package coding.faizal.ecommerce.core.data.source.remote.network.product



import coding.faizal.ecommerce.core.data.source.remote.response.SingleResponse
import coding.faizal.ecommerce.core.data.source.remote.response.product.ListProductResponse
import coding.faizal.ecommerce.core.data.source.remote.response.product.SingleProductResponse
import coding.faizal.ecommerce.core.data.source.remote.response.product.payment.PaymentRequest
import coding.faizal.ecommerce.core.data.source.remote.response.product.payment.PaymentResponse
import coding.faizal.ecommerce.data.source.remote.response.product.*

import retrofit2.http.*

interface ApiProductService {

    @GET("products")
    suspend fun getAllProduct() : ListProductResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id : String) : SingleProductResponse

    @GET("user/wishlist")
    suspend fun getAllWishlist() : SingleResponse<ListWishlistResponse>

    @POST("user/wishlist/add")
    suspend fun addWishlist(@Body wishlistRequest : WishlistRequest) : SingleResponse<ListWishlistResponse>

    @DELETE("user/wishlist/remove/{productId}")
    suspend fun deleteWishlist(@Path("productId") id : String) : SingleResponse<ListWishlistResponse>

    @POST("order/create")
    suspend fun doOrder(@Body orderItemRequest: OrderItemRequestList) : OrderResponse


    @POST("payment/process")
    suspend fun doPayment(@Body paymentRequest: PaymentRequest) : PaymentResponse


}