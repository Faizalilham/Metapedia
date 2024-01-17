package coding.faizal.ecommerce.core.domain.model.remote.product

data class Wishlist(
    val product :  String
)

data class ListWishlist(
    val wishlist : List<coding.faizal.ecommerce.core.domain.model.remote.product.Wishlist>
)
