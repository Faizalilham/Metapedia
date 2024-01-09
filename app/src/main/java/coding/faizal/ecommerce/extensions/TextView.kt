package coding.faizal.ecommerce.extensions

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import coding.faizal.ecommerce.R

fun TextView.spanText(
    text : TextView,
    textString : String,
    start : Int,
    end : Int,
    ctx : Context,
    onClick : () -> Unit
){

    val ss = SpannableString(textString)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = ContextCompat.getColor(ctx, R.color.primary_color)
            ds.isFakeBoldText = true
        }

        override fun onClick(p0: View) {
            onClick()
        }
    }
    ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    text.text = ss
    text.movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.doubleSpanText(
    texts : TextView,
    textString : String,
    startFirst : Int,
    endFirst : Int,
    startSecond : Int,
    endSecond : Int,
    ctx : Context,
    onClickFirst : () -> Unit,
    onClickSecond : () -> Unit
){

    val ss = SpannableString(textString)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = ContextCompat.getColor(ctx, R.color.primary_color)
            ds.isFakeBoldText = true
        }

        override fun onClick(p0: View) {
            onClickFirst()
        }
    }
    val clickableSpan2: ClickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = ContextCompat.getColor(ctx, R.color.primary_color)
            ds.isFakeBoldText = true
        }

        override fun onClick(p0: View) {
            onClickSecond()
        }
    }
    ss.setSpan(clickableSpan, startFirst, endFirst, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    ss.setSpan(clickableSpan2, startSecond, endSecond, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    texts.text = ss
    texts.movementMethod = LinkMovementMethod.getInstance()

}