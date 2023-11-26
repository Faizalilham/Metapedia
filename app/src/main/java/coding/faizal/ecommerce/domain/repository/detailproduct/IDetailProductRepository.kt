package coding.faizal.ecommerce.domain.repository.detailproduct

import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize

interface IDetailProductRepository {

    fun getAllSizeProduct() : List<ProductSize>
}