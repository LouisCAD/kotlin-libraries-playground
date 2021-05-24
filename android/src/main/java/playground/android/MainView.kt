package playground.android

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.updatePadding
import com.squareup.contour.ContourLayout

class MainView(context: Context, attrs: AttributeSet? = null) : ContourLayout(context, attrs) {
    private val toolbar = TextView(context).apply {
        gravity = Gravity.CENTER_VERTICAL
        letterSpacing = 0.05f
        text = "Enter your details"
        textSize = 18f
        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        setTextColor(Color.WHITE)
        updatePadding(left = 26.dip)
    }
    private var name = EditText(context).apply {
        hint = "Name"
        textSize = 18f
        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    }
    private var email = EditText(context).apply {
        hint = "Email"
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        textSize = 18f
        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    }
    var password = EditText(context).apply {
        hint = "Password"
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        textSize = 18f
        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    }

    private var confirmPassword = EditText(context).apply {
        hint = "Confirm Password"
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        textSize = 18f
        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    }
    private val submitBtn = Button(context).apply {
        text = "Submit"
        setBackgroundResource(R.drawable.btn_shape)


    }

    init {

        toolbar.layoutBy(
            x = leftTo { parent.left() },
            y = topTo { parent.top() + 4.ydip }.heightOf { 56.ydip }
        )
        name.layoutBy(
            x = leftTo { parent.left() + 50.xdip }.rightTo { parent.right() - 10.xdip },
            y = bottomTo { toolbar.top() + 120.ydip }
        )

        email.layoutBy(
            x = leftTo { parent.left() + 50.xdip }.rightTo { parent.right() - 10.xdip },
            y = bottomTo { name.bottom() + 60.ydip }
        )

        password.layoutBy(
            x = leftTo { parent.left() + 50.xdip }.rightTo { parent.right() - 10.xdip },
            y = bottomTo { email.bottom() + 60.ydip }

        )
        confirmPassword.layoutBy(
            x = leftTo { parent.left() + 50.xdip }.rightTo { parent.right() - 10.xdip },
            y = bottomTo { password.bottom() + 60.ydip }

        )
        submitBtn.layoutBy(
            x = leftTo { parent.left() + 50.xdip }.rightTo { parent.right() - 100.xdip },
            y = bottomTo { confirmPassword.bottom() + 80.ydip }
        )
    }
}
