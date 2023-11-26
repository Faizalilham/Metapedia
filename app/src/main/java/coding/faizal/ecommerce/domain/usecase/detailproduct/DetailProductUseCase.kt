package coding.faizal.ecommerce.domain.usecase.detailproduct

import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize

interface DetailProductUseCase {

    fun getAllSizeProduct() : List<ProductSize>
}