package coding.faizal.ecommerce.domain.model.remote.product

data class Wishlist(
    val product :  String
)

data class ListWishlist(
    val wishlist : List<Wishlist>
)
