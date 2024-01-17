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
    suspend fun getAllProduct(@Header("Authorization") token : String,) : ListProductResponse

    @GET("products/{id}")
    suspend fun getProductById(@Header("Authorization") token : String,@Path("id") id : String) : SingleProductResponse

    @GET("user/wishlist")
    suspend fun getAllWishlist(@Header("Authorization") token : String) : SingleResponse<ListWishlistResponse>

    @POST("user/wishlist/add")
    suspend fun addWishlist(@Header("Authorization") token : String,@Body wishlistRequest : WishlistRequest) : SingleResponse<ListWishlistResponse>

    @DELETE("user/wishlist/remove/{productId}")
    suspend fun deleteWishlist(@Header("Authorization") token : String,@Path("productId") id : String) : SingleResponse<ListWishlistResponse>

    @POST("order/create")
    suspend fun doOrder(@Header("Authorization") token : String,@Body orderItemRequest: OrderItemRequestList) : OrderResponse


    @POST("payment/process")
    suspend fun doPayment(@Header("Authorization") token : String,@Body paymentRequest: PaymentRequest) : PaymentResponse


}