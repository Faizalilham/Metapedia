package coding.faizal.ecommerce.domain.model.remote.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize




@Parcelize
data class Product(
    val images: ProductImages,
    val _id: String,
    val name: String,
    val subtitle: String,
    val description: String,
    val variants: List<ProductVariant>,
    val category: String,
    val price: String,
    val featured: Boolean,
    val imgSrc: List<String>
) : Parcelable

@Parcelize
data class ProductImages(
    val main: String,
    val sub: String
) : Parcelable

@Parcelize
data class ProductVariant(
    val color: String,
    val size: String,
    val quantity: Int
) : Parcelable
