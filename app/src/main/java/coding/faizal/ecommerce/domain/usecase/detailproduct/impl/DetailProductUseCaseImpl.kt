package coding.faizal.ecommerce.domain.usecase.detailproduct.impl

import coding.faizal.ecommerce.di.detailproduct.ProvideDetailProductRepository
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize
import coding.faizal.ecommerce.domain.repository.detailproduct.IDetailProductRepository
import coding.faizal.ecommerce.domain.usecase.detailproduct.DetailProductUseCase
import javax.inject.Inject

class DetailProductUseCaseImpl @Inject constructor(
    @ProvideDetailProductRepository private val IDetailProductRepository :IDetailProductRepository
    ) : DetailProductUseCase {
    override fun getAllSizeProduct(): List<ProductSize> = IDetailProductRepository.getAllSizeProduct()
}