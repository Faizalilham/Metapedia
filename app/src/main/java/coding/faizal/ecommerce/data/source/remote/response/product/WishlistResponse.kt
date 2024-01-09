package coding.faizal.ecommerce.data.source.remote.response.product

data class WishlistResponse(
    val product :   String
)

data class ListWishlistResponse(
    val wishlist : List<WishlistResponse>
)

data class WishlistRequest(
    val productId : String
)
