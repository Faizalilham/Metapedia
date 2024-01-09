package coding.faizal.ecommerce.domain.model.remote.product

data class ProductOrder(

    val id: String,

    val user: String,

    val orderItems: List<OrderItem>,

    val paymentMethod: String,
)

data class OrderItem(
    val id: String,

    val product: String,

    val quantity: Int,

    val price: String
)

data class Order(
    val status: String,
    val order: ProductOrder
)
