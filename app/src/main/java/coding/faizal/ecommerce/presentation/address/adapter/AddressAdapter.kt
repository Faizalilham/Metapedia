package coding.faizal.ecommerce.presentation.address.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.ListItemAddressBinding
import coding.faizal.ecommerce.databinding.ListItemAddressShimmerBinding
import coding.faizal.ecommerce.databinding.ProductSizeItemBinding
import coding.faizal.ecommerce.domain.model.local.address.LabelAddress

class AddressAdapter (private val context : Context,
                         private val datas : List<LabelAddress>,
                         private val listener : AddressOnClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val ITEM_ACTUAL = 0
    private val ITEM_SHIMMER = 1

    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    init{
        setSelected(0)
    }


    inner class AddressViewHolder(val binding : ListItemAddressBinding) : RecyclerView.ViewHolder(binding.root){

        fun defaultBg() {
            binding.apply {
                card.background = context.getDrawable(R.drawable.background_unselect_size)
                tvLabelAddress.setTextColor(context.getColor(R.color.semi_black))
            }
        }

        fun selectedBg() {
            binding.apply {
                card.background = context.getDrawable(R.drawable.background_selected_size)
                tvLabelAddress.setTextColor(context.getColor(R.color.primary_color))
            }

        }

    }

    inner class AddressShimmerViewHolder(val binding : ListItemAddressShimmerBinding) : RecyclerView.ViewHolder(binding.root){
        fun showShimmer(){
            binding.shimmerViewContainer.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_ACTUAL -> {
                return AddressViewHolder(ListItemAddressBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            ITEM_SHIMMER -> {
                AddressShimmerViewHolder(ListItemAddressShimmerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is AddressViewHolder){
            holder.binding.apply {
                val data = datas[position]
                if(data.name.isNotEmpty()){
                    val split = data.name.split(",")
                    tvLabelAddress.text = split[0]
                    tvName.text = split[1]
                    tvPhoneNumber.text = split[2]
                    tvAddress.text = split[3]
                    if(split.size > 4){
                        val address = "${split[3]} (${split[4]})"
                        tvAddress.text = address
                    }
                }
                holder.defaultBg()
                if(position == selectedItemPos)
                    holder.selectedBg()
                else
                    holder.defaultBg()

                card.setOnClickListener {
                    listener.labelAddress(data,position)
                    selectedItemPos = position
                    lastItemSelectedPos = if(lastItemSelectedPos == -1)
                        selectedItemPos
                    else {
                        notifyItemChanged(lastItemSelectedPos)
                        selectedItemPos
                    }
                    notifyItemChanged(selectedItemPos)
                }
                btnAngeAddress.setOnClickListener {
                    listener.moveToAdd(data)
                }
            }
        }else if(holder is AddressShimmerViewHolder){
            holder.showShimmer()
        }
    }

    fun setSelected(position: Int) {
        for (i in datas.indices) {
            datas[i].isSelected = (i == position)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (datas.isNotEmpty()) {
            ITEM_ACTUAL
        } else {
            ITEM_SHIMMER
        }
    }

    override fun getItemCount(): Int  = if(datas.isEmpty()) 12 else datas.size

    interface AddressOnClick{
        fun labelAddress(labelAddress : LabelAddress, position : Int)
        fun moveToAdd(labelAddress: LabelAddress)
    }
}