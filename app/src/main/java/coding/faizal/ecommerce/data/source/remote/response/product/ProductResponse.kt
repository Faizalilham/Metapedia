package coding.faizal.ecommerce.data.source.remote.response.product

import android.os.Parcelable
import coding.faizal.ecommerce.domain.model.remote.product.ProductImages
import coding.faizal.ecommerce.domain.model.remote.product.ProductVariant
import kotlinx.parcelize.Parcelize


@Parcelize
data class ListProductResponse(
    val code: Int,
    val status: String,
    val results: Int,
    val data: ListProductData
) : Parcelable

@Parcelize
data class SingleProductResponse(
    val code: Int,
    val status: String,
    val data: SingleProductData
) : Parcelable

@Parcelize
data class ListProductData(
    val products: List<ProductResponse>
) : Parcelable

@Parcelize
data class SingleProductData(
    val product: ProductResponse
) : Parcelable


@Parcelize
data class ProductResponse(
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
data class ProductImagesResponse(
    val main: String,
    val sub: String
) : Parcelable

@Parcelize
data class ProductVariantResponse(
    val color: String,
    val size: String,
    val quantity: Int
) : Parcelable
