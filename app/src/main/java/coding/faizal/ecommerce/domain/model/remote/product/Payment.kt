package coding.faizal.ecommerce.domain.model.remote.product



data class Payment(

    val token: String,

    val redirectUrl: String
)