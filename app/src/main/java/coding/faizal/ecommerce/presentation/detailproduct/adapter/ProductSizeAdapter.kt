package coding.faizal.ecommerce.presentation.detailproduct.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ProductSizeItemBinding
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductSize

class ProductSizeAdapter (
    private val context : Context,
    private val datas : List<ProductSize>,
    private val listener : ProductSizeOnClick
        ) : RecyclerView.Adapter<ProductSizeAdapter.ProductSizeViewHolder>(){

    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    init{
        setSelected(0)
    }


    inner class ProductSizeViewHolder(val binding : ProductSizeItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun defaultBg() {
            binding.apply {
                card.background = context.getDrawable(R.drawable.background_unselect_size)
                textProductSize.setTextColor(context.getColor(R.color.semi_black))
            }
        }

        fun selectedBg() {
            binding.apply {
                card.background = context.getDrawable(R.drawable.background_selected_size)
                textProductSize.setTextColor(context.getColor(R.color.primary_color))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSizeViewHolder {
        return ProductSizeViewHolder(ProductSizeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductSizeViewHolder, position: Int) {
       holder.binding.apply {
           val data = datas[position]
           textProductSize.text = data.size
           holder.defaultBg()
           if(position == selectedItemPos)
               holder.selectedBg()
           else
               holder.defaultBg()

           card.setOnClickListener {
               listener.productSize(data,position)
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
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = datas.size

    interface ProductSizeOnClick{
        fun productSize(productSize : ProductSize,position : Int)
    }
}