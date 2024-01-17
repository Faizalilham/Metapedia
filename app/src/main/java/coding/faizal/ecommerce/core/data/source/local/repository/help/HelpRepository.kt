package coding.faizal.ecommerce.core.data.source.local.repository.help

import coding.faizal.ecommerce.core.domain.model.local.help.Help
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HelpRepository @Inject constructor() : IHelpRepository {
    override fun getAllData(): List<coding.faizal.ecommerce.core.domain.model.local.help.Help> {
       return listOf(
           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Saya Tidak Dapat Mengakses Akun Metapedia",
               "Jika Anda mengalami kesulitan mengakses akun Metapedia, pastikan koneksi internet Anda stabil. Selain itu, periksa apakah Anda menggunakan informasi login yang benar. Jika masalah masih berlanjut, coba ulangi proses pengaturan ulang kata sandi atau hubungi dukungan pelanggan Metapedia."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Saya Tidak Bisa Login Metapedia",
               "Jika Anda mengalami masalah saat mencoba login ke akun Metapedia, pastikan bahwa nama pengguna dan kata sandi yang Anda masukkan benar. Jika Anda lupa kata sandi, gunakan opsi pemulihan kata sandi atau hubungi dukungan pelanggan Metapedia untuk bantuan lebih lanjut."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Jenis Pembayaran Apa Saja yang Tersedia di Metapedia?",
               "Metapedia menyediakan berbagai jenis pembayaran untuk memberikan fleksibilitas kepada pengguna. Anda dapat menggunakan kartu kredit, transfer bank, atau metode pembayaran elektronik lainnya yang didukung oleh platform. Pastikan untuk memeriksa opsi pembayaran yang tersedia di bagian pengaturan akun Anda."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Apa itu Search Bar?",
               "Search Bar adalah fitur pencarian di Metapedia yang memungkinkan Anda dengan mudah menemukan informasi yang Anda butuhkan. Anda dapat memasukkan kata kunci, topik, atau pertanyaan untuk menelusuri konten Metapedia. Gunakan Search Bar untuk mendapatkan akses cepat dan efisien ke berbagai artikel dan informasi yang tersedia."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Bagaimana cara belanja produk di Metapedia?",
               "Untuk berbelanja produk di Metapedia, Anda dapat mengikuti langkah-langkah berikut:\n\n1. Login ke akun Metapedia Anda.\n2. Telusuri katalog produk atau gunakan fitur pencarian untuk menemukan produk yang Anda inginkan.\n3. Pilih produk yang ingin Anda beli dan tambahkan ke keranjang belanja.\n4. Lanjutkan ke halaman checkout untuk memasukkan detail pengiriman dan pembayaran.\n5. Pilih metode pembayaran yang diinginkan dan selesaikan transaksi.\n\nJika Anda mengalami kendala atau memiliki pertanyaan lebih lanjut, jangan ragu untuk menghubungi dukungan pelanggan Metapedia."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Apa itu voucher promo?",
               "Voucher promo adalah tawaran diskon khusus yang dapat digunakan saat berbelanja di Metapedia. Anda dapat memperoleh voucher promo melalui berbagai cara, seperti program loyalitas, promosi khusus, atau melalui mitra Metapedia. Untuk menggunakan voucher promo, masukkan kode voucher yang valid saat proses checkout. Pastikan untuk memeriksa syarat dan ketentuan yang terkait dengan setiap voucher promo untuk memastikan penggunaan yang benar dan mendapatkan manfaat maksimal dari penawaran tersebut."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Mengapa kode verifikasi email tidak terkirim?",
               "Jika Anda mengalami kesulitan menerima kode verifikasi email, pertimbangkan langkah-langkah berikut:\n\n1. Pastikan bahwa alamat email yang Anda daftarkan benar dan aktif.\n2. Periksa folder spam atau kotak surat sampah di kotak masuk email Anda. Kode verifikasi mungkin terdeteksi sebagai email spam.\n3. Periksa apakah layanan email Anda mengalami gangguan atau masalah teknis.\n4. Pastikan koneksi internet Anda stabil.\n\nJika setelah melakukan langkah-langkah tersebut masih mengalami masalah, segera hubungi dukungan pelanggan Metapedia untuk bantuan lebih lanjut."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Bagaimana cara mendaftar di Metapedia?",
               "Berikut adalah langkah-langkah untuk mendaftar di Metapedia:\n\n1. Buka aplikasi Metapedia.\n2. Pilih opsi 'Daftar' atau 'Sign Up'.\n3. Masukkan alamat email yang valid pada kolom yang disediakan.\n4.Kode verifikasi akan terkirim ke email yang ditulis sebelumnya.\n5. Buka kotak masuk email Anda dan temukan email dengan subjek 'Kode Verifikasi Metapedia'.\n6. Salin kode verifikasi dari email dan tempelkan pada aplikasi Metapedia.\n7. Setelah verifikasi berhasil, masukkan username yang ingin Anda gunakan.\n8. Isi informasi akun lainnya, seperti kata sandi dan detail pribadi.\n9. Klik tombol 'Daftar' atau 'Sign Up' untuk menyelesaikan proses pendaftaran.\n10. Akun Metapedia Anda sekarang telah berhasil dibuat!\n\nPastikan untuk mengingat informasi login Anda dan selalu menjaga keamanan akun Anda."
           ),

           coding.faizal.ecommerce.core.domain.model.local.help.Help(
               "Apa itu fitur Wishlist produk di Metapedia?",
               "Fitur Wishlist di Metapedia memungkinkan Anda untuk menyimpan produk yang Anda minati tanpa harus langsung membelinya. Berikut adalah beberapa hal yang dapat Anda lakukan dengan fitur Wishlist:\n\n1. **Simpan Produk Favorit**: Tambahkan produk yang menarik perhatian Anda ke dalam Wishlist untuk menyimpannya dan mengaksesnya nanti.\n2. **Pantau Harga**: Dapatkan pemberitahuan jika harga produk di Wishlist mengalami perubahan.\n3. **Perbandingan Produk**: Bandingkan produk di Wishlist sebelum membuat keputusan pembelian.\n4. **Pemberitahuan Ketersediaan Stok**: Terima pemberitahuan ketika produk yang Anda simpan kembali tersedia.\n\nAnda dapat mengakses dan mengelola Wishlist Anda melalui akun Metapedia Anda. Fitur ini dirancang untuk memberikan pengalaman berbelanja yang lebih terorganisir dan sesuai dengan preferensi pribadi Anda."
           )


       )
    }
}