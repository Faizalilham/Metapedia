package coding.faizal.ecommerce.core.domain.model.remote.product

data class ProductOrder(

    val id: String,

    val user: String,

    val orderItems: List<coding.faizal.ecommerce.core.domain.model.remote.product.OrderItem>,

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
    val order: coding.faizal.ecommerce.core.domain.model.remote.product.ProductOrder
)
