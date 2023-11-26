package coding.faizal.ecommerce.domain.model.local.detailproduct

data class ProductImage(
    val id : Int,
    val color : String,
    val image : String,
    var isSelected : Boolean
)
