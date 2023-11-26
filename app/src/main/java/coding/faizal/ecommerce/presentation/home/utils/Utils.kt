package coding.faizal.ecommerce.presentation.home.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.TooltipLayoutBinding
import com.fenchtose.tooltip.Tooltip
import com.fenchtose.tooltip.TooltipAnimation
import java.text.SimpleDateFormat
import java.util.*


object Utils {


    fun formatDateRanges(daysToAdd: Int, formatStart: String, formatEnd: String): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, 3)

        val startDate = SimpleDateFormat("dd", Locale.getDefault()).format(currentDate.time)

        currentDate.add(Calendar.DAY_OF_MONTH, 2)
        val endDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(currentDate.time)

        return "$startDate - $endDate"
    }


    private fun updateViewsVisibility(linearLayout: LinearLayout, opacity: Float) {
        if (opacity < 0.3) {
            linearLayout.animate()
                .translationX(-linearLayout.width.toFloat())
                .alpha(0.5f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        linearLayout.visibility = View.GONE
                    }
                })
        } else {
            val transition: Transition = Slide(Gravity.END)
            transition.duration = 300
            (transition as Slide).slideEdge = Gravity.START

            if (linearLayout.visibility != View.VISIBLE) {
                linearLayout.visibility = View.VISIBLE
                linearLayout.requestLayout()
                TransitionManager.beginDelayedTransition(linearLayout, transition)
            }

        }
    }

    fun setupPageTransformer() : CompositePageTransformer{
       val MIN_SCALE = 0.85f
       val MIN_ALPHA = 0.5f
       val transformer = CompositePageTransformer()
       transformer.addTransformer(MarginPageTransformer(40))
       transformer.addTransformer { page, position ->
           val pageWidth = page.width
           val pageHeight = page.height

           when {
               position < -1 -> {
                   page.alpha = 0f
               }
               position <= 1 -> {

                   val scaleFactor = Math.max(MIN_SCALE, 1 - kotlin.math.abs(position))
                   val vertMargin = pageHeight * (1 - scaleFactor) / 2
                   val horzMargin = pageWidth * (1 - scaleFactor) / 2
                   if (position < 0) {
                       page.translationX = horzMargin - vertMargin / 2
                   } else {
                       page.translationX = -horzMargin + vertMargin / 2
                   }

                   page.scaleX = scaleFactor
                   page.scaleY = scaleFactor

                   page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) /
                           (1 - MIN_SCALE) * (1 - MIN_ALPHA)

               }
               else -> {
                   page.alpha = 0f
               }
           }

       }
       return transformer
   }

    fun showCustomTooltip(anchor: View,context : Context,viewGroup : ViewGroup,layoutInflater : LayoutInflater,content : String) {

        val view = TooltipLayoutBinding.inflate(layoutInflater)

        view.tvContent.text = content
        val tooltipColor = ContextCompat.getColor(context, R.color.semi_black)

        Tooltip.Builder(context)
            .anchor(anchor, Tooltip.BOTTOM)
            .animate(TooltipAnimation(TooltipAnimation.SCALE_AND_FADE, 400))
            .withPadding(16)
            .content(view.root)
            .withTip(Tooltip.Tip(30, 30, tooltipColor))
            .into(viewGroup)
            .debug(true)
            .show()
    }


}

