package coding.faizal.ecommerce.presentation.home.adapter

import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.databinding.ProductItemBinding
import coding.faizal.ecommerce.presentation.home.utils.Utils.formatDateRanges

class ProductAdapter(
    private var data : List<String>, private val listener : OnClickProduct
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding : ProductItemBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            val result = "Tiba ${formatDateRanges(3,"dd","MMM")}"

            textUntil.text = result
            imgFavorite.setOnClickListener {
                listener.showBottomNav()
            }
            card.setOnClickListener { listener.productDetail() }
        }
    }

    override fun getItemCount(): Int  = data.size


    interface OnClickProduct{
        fun showBottomNav()
        fun productDetail()
    }
}