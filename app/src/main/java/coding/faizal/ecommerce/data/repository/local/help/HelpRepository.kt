package coding.faizal.ecommerce.data.repository.local.help

import coding.faizal.ecommerce.domain.model.local.help.Help
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HelpRepository @Inject constructor() : IHelpRepository {
    override fun getAllData(): List<Help> {
       return listOf(
           Help("Saya Tidak Dapat Mengakses Akun Metapedia", "Help Description 1"),
           Help("Saya Tidak Bisa Login Metapedia", "Help Description 2"),
           Help("Jenis Pembayaran Apa Saja yang Tersedia di Metapedia?", "Help Description 2"),
           Help("Apa itu Search Bar?", "Help Description 2"),
       )
    }
}