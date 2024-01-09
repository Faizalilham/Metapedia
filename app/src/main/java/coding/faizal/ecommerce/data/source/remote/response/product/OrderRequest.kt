package coding.faizal.ecommerce.data.source.remote.response.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItemRequest(
    val product: String,
    val variant: String,
    val quantity: Int,
    val price: String
):Parcelable

@Parcelize
data class OrderItemRequestList(
    val orderItems : List<OrderItemRequest>
):Parcelable
