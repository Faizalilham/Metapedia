package coding.faizal.ecommerce.presentation.home.adapter


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.databinding.ListProductItemShimmerBinding
import coding.faizal.ecommerce.databinding.ProductItemBinding
import coding.faizal.ecommerce.domain.model.remote.product.Product
import coding.faizal.ecommerce.presentation.home.utils.Utils.formatDateRanges
import com.bumptech.glide.Glide

class ProductAdapter(
    private var data : List<Product>, private val listener : OnClickProduct
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_ACTUAL = 0
    private val ITEM_SHIMMER = 1

    inner class ProductViewHolder(val binding : ProductItemBinding ) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : List<Product>, position : Int){
            binding.apply {
                Glide.with(root)
                    .load(data[position].images.main)
                    .into(imageProduct)
                textTittle.text = data[position].name
                val price = "Rp${data[position].price}"
                textPrice.text = price
                val totalQuantity: Int = data[position].variants.sumOf { it.quantity }
                textSold.text = data[position].category
                textAverage.text = totalQuantity.toString()

                val result = "Tiba ${formatDateRanges(3,"dd","MMM")}"
                textUntil.text = result
                imgFavorite.setOnClickListener {
                    listener.showBottomNav(data[position])
                }
                card.setOnClickListener { listener.productDetail(data[position]) }
            }
        }
    }

    inner class ProductShimmerViewHolder(val binding : ListProductItemShimmerBinding) : RecyclerView.ViewHolder(binding.root){
        fun startShimmerEffect() {
            binding.shimmerViewContainer.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_ACTUAL -> {
                return ProductViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            ITEM_SHIMMER -> {
                ProductShimmerViewHolder(ListProductItemShimmerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            if(data.isNotEmpty()){
                holder.bind(data,position)
            }
        } else if (holder is ProductShimmerViewHolder) {
            holder.startShimmerEffect()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty()) {
            ITEM_ACTUAL
        } else {
            ITEM_SHIMMER
        }
    }

    override fun getItemCount(): Int  = if(data.isEmpty()) 12 else data.size



    interface OnClickProduct{
        fun showBottomNav(data : Product)
        fun productDetail( data : Product)
    }
}