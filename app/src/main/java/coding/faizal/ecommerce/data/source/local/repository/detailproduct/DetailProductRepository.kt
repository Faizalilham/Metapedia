package coding.faizal.ecommerce.data.source.local.repository.detailproduct

import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize
import coding.faizal.ecommerce.domain.repository.detailproduct.IDetailProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailProductRepository @Inject constructor(): IDetailProductRepository {
    override fun getAllSizeProduct(): List<ProductSize> {
       return listOf(
           ProductSize(1,"S",false),
           ProductSize(2,"M",false),
           ProductSize(3,"L",false),
           ProductSize(4,"XL",false),
           ProductSize(5,"XXL",false),
           ProductSize(6,"XXXL",false),
           ProductSize(7,"XXXXL",false)
       )
    }
}