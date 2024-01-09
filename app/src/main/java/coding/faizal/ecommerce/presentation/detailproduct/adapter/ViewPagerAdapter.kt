package coding.faizal.ecommerce.presentation.detailproduct.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.domain.model.local.detailproduct.ProductImage
import com.bumptech.glide.Glide

class ViewPagerAdapter(private val context: Context, private val images: List<ProductImage>) : PagerAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.viewpager_image, container, false)

        val imageView: ImageView = view.findViewById(R.id.idIVImage)

        Glide.with(context)
            .load(images[position].image)
            .into(imageView)

        container.addView(view)
        return view
    }



    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
