package coding.faizal.ecommerce.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.text.*
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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


    fun hideKeyboard(context: Context, view : View) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }




    fun toggleShow(show: Boolean,parent : ViewGroup,target : ViewGroup) {
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = 600
        transition.addTarget(target)
        TransitionManager.beginDelayedTransition(parent, transition)
        target.visibility = if (show) View.VISIBLE else View.GONE
    }

}

class ReusableTextWatcher(private val et : EditText? = null, private val textChangedAction: (String) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        textChangedAction(s.toString())

        if (et != null) {
            if (s.isNullOrBlank()) {
                et.error = "Harus diisi"
                setEditTextErrorState(et)
            } else if (s.length < 8) {
                et.error = "Minimal 8 Karakter"
                setEditTextErrorState(et)
            } else {
                et.error = null
                setEditTextNormalState(et)
            }
        }
    }

    private fun setEditTextErrorState(editText: EditText) {
        val errorColor = ContextCompat.getColor(editText.context, R.color.red)
        editText.backgroundTintList = ColorStateList.valueOf(errorColor)
    }

    private fun setEditTextNormalState(editText: EditText) {
        val normalColor = ContextCompat.getColor(editText.context, R.color.primary_color)
        editText.backgroundTintList = ColorStateList.valueOf(normalColor)
    }
}



