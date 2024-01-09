package coding.faizal.ecommerce.data.source.remote.response.product

import coding.faizal.ecommerce.domain.model.remote.product.OrderItem
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ProductOrderResponse(
    @SerializedName("user")
    val user: String,

    @SerializedName("orderItems")
    val orderItems: List<OrderItem>,

    @SerializedName("paymentMethod")
    val paymentMethod: String,

    @SerializedName("_id")
    val id: String,

    @SerializedName("__v")
    val version: Int
)



data class OrderResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("order")
    val order: ProductOrderResponse
)

