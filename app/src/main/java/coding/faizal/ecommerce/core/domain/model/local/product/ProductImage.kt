package coding.faizal.ecommerce.core.domain.model.local.product

data class ProductImage(
    val id : Int,
    val color : String,
    val image : String,
    var isSelected : Boolean
)
