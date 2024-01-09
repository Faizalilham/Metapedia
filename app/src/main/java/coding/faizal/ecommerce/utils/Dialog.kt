package coding.faizal.ecommerce.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.*
import androidx.appcompat.app.AlertDialog
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.LoginDialogBinding
import coding.faizal.ecommerce.databinding.LogoutDialogBinding
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraLogin


object Dialog {


        fun showCustomDialog(context: Context) {

            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.alert_success_cart, null)


            val builder = AlertDialog.Builder(context)
                .setView(view)

            val alertDialog = builder.create()

            alertDialog.window?.attributes?.windowAnimations = R.style.PauseDialogAnimation

            alertDialog.show()

            val handler = Handler()
            handler.postDelayed({

                alertDialog.dismiss()
            }, 2000)
        }

    fun showDialogLogin(activity: Activity,tittleText : String,subTitleText : String,buttonText : String, email: String,actionButton : () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(activity)
        val view = LoginDialogBinding.inflate(activity.layoutInflater)

        dialogBuilder.setView(view.root)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.attributes?.windowAnimations = R.style.PauseDialogAnimation
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.apply {
            val subtitleText = "$subTitleText $email?"
            tittle.text = tittleText
            subtittle.text = subtitleText
            btnLogin.text = buttonText
            btnLogin.setOnClickListener { actionButton() }
            close.setOnClickListener { alertDialog.dismiss() }
        }

        alertDialog.show()
    }

    fun showDialogLogout(activity: Activity,action : () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(activity)
        val view = LogoutDialogBinding.inflate(activity.layoutInflater)

        dialogBuilder.setView(view.root)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.attributes?.windowAnimations = R.style.PauseDialogAnimation
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.apply {
            btnLogout.setOnClickListener {
                action()
            }
            close.setOnClickListener { alertDialog.dismiss() }
        }

        alertDialog.show()
    }

}
