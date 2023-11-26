package coding.faizal.ecommerce.presentation.detailproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ProductSizeItemBinding
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize

class ProductSizeAdapter (
    private val datas : List<ProductSize>,
    private val listener : ProductSizeOnClick
        ) : RecyclerView.Adapter<ProductSizeAdapter.ProductSizeViewHolder>(){

    inner class ProductSizeViewHolder(val binding : ProductSizeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSizeViewHolder {
        return ProductSizeViewHolder(ProductSizeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductSizeViewHolder, position: Int) {
       holder.binding.apply {
           val data = datas[position]
           textProductSize.text = data.size
           if (data.isSelected) {
               card.setBackgroundColor(R.drawable.background_selected_size)
           } else {
               card.setBackgroundColor(R.drawable.background_unselect_size)
           }

           card.setOnClickListener { listener.productSize(data,position) }
       }
    }

    fun setSelected(position: Int) {
        for (i in datas.indices) {
            datas[i].isSelected = (i == position)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = datas.size

    interface ProductSizeOnClick{
        fun productSize(productSize : ProductSize,position : Int)
    }
}