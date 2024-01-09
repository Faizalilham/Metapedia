package coding.faizal.ecommerce.presentation.detailproduct.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ProductColorItemBinding
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductImage
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

data class ProductColorAdapter(
    private val context : Context,
    private val datas : List<ProductImage>,
    private val listener : ProductImageOnClick,
    private val btn : MaterialButton,
) : RecyclerView.Adapter<ProductColorAdapter.ProductColorViewHolder>(){

    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    inner class ProductColorViewHolder(val binding : ProductColorItemBinding): RecyclerView.ViewHolder(binding.root){

        fun defaultBg() {
            binding.apply {
                card.background = context.getDrawable(R.drawable.background_unselect_size)
                textColorProduct.setTextColor(context.getColor(R.color.semi_black))
            }
        }

        fun selectedBg() {
            binding.apply {
                card.background = context.getDrawable(R.drawable.background_selected_size)
                textColorProduct.setTextColor(context.getColor(R.color.primary_color))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductColorViewHolder {
        return ProductColorViewHolder(ProductColorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductColorViewHolder, position: Int) {
        holder.binding.apply {
            val data = datas[position]
            textColorProduct.text = data.color

            Glide.with(context)
                .load(data.image)
                .into(imgProduct)

            Glide.with(context)
                .load(data.image)
                .into(imgProductCopy)

            btn.setOnClickListener { listener.image(imgProductCopy) }

            holder.defaultBg()
            if(position == selectedItemPos)
                holder.selectedBg()
            else
                holder.defaultBg()

            if(data.isSelected){
                holder.selectedBg()
            }else{
                holder.defaultBg()
            }

            card.setOnClickListener {
                listener.productImage(data,position)
                selectedItemPos = position
                lastItemSelectedPos = if(lastItemSelectedPos == -1)
                    selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }

        }
    }

    fun setSelected(position: Int) {
        for (i in datas.indices) {
            datas[i].isSelected = (i == position)
        }
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = datas.size

    interface ProductImageOnClick{
        fun productImage(productImage : ProductImage,position : Int)
        fun image(imageView : ImageView)
    }

}