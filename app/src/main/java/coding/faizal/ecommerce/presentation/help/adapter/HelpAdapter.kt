package coding.faizal.ecommerce.presentation.help.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.HelperItemBinding
import coding.faizal.ecommerce.domain.model.local.help.Help

class HelpAdapter(private val data : List<Help>) : RecyclerView.Adapter<HelpAdapter.HelpViewHolder>() {

    inner class HelpViewHolder(val binding : HelperItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
       return HelpViewHolder(HelperItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
       holder.binding.apply {
           textTittle.text = data[position].name
           textDescription.text = data[position].description
           linearLayout.setOnClickListener {
               if (expandableLayout.isExpanded) {
                   expandableLayout.collapse()
                   arrowLinearFlight.setImageResource(R.drawable.ic_arrow_down)
               } else {
                   expandableLayout.expand()
                   arrowLinearFlight.setImageResource(R.drawable.ic_arrow_up)
               }
           }

       }
    }

    override fun getItemCount(): Int  = data.size
}