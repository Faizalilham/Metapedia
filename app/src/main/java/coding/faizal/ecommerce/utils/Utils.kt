package coding.faizal.ecommerce.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.*
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.PopupMenu
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.presentation.help.screen.HelpActivity
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity


object UiUtil {

    fun showPopupMenu(context: Context, anchorView: View, menuResId: Int) {
        val popupMenu = PopupMenu(context, anchorView)
        popupMenu.menuInflater.inflate(menuResId, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    context.startActivity(Intent(context, HomeActivity::class.java).also { (context as Activity).finish() })
                    true
                }
                R.id.action_help -> {
                    context.startActivity(Intent(context, HelpActivity::class.java).also { (context as Activity).finish() })
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }




    fun toggleShow(show: Boolean,parent : ViewGroup,target : ViewGroup) {
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = 600
        transition.addTarget(target)
        TransitionManager.beginDelayedTransition(parent, transition)
        target.visibility = if (show) View.VISIBLE else View.GONE
    }


}



