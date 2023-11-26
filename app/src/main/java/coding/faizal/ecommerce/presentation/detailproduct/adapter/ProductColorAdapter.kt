package coding.faizal.ecommerce.presentation.detailproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ProductColorItemBinding
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductImage

data class ProductColorAdapter(
    private val datas : List<ProductImage>,
    private val listener : ProductImageOnClick
) : RecyclerView.Adapter<ProductColorAdapter.ProductColorViewHolder>(){

    inner class ProductColorViewHolder(val binding : ProductColorItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductColorViewHolder {
        return ProductColorViewHolder(ProductColorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductColorViewHolder, position: Int) {
        holder.binding.apply {
            val data = datas[position]
            textColorProduct.text = data.color
            if (data.isSelected) {
                card.setBackgroundColor(R.drawable.background_selected_size)
            } else {
               card.setBackgroundColor(R.drawable.background_unselect_size)
            }

            card.setOnClickListener {
                listener.productImage(data,position)
            }

        }
    }

    fun setSelected(position: Int) {
        for (i in datas.indices) {
            datas[i].isSelected = (i == position)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = datas.size

    interface ProductImageOnClick{
        fun productImage(productImage : ProductImage,position : Int)
    }

}