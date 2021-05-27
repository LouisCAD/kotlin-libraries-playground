package playground.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ContourSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainView(this))

    }

}
