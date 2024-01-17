package coding.faizal.ecommerce.core.domain.model.remote.product



data class Payment(

    val token: String,

    val redirectUrl: String
)