package playground.android

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

data class LibraryName(var name:String, var fragment:Fragment)


object Suppliers{
    val libraries = listOf(LibraryName("Contour",ContourFragment()),
                            LibraryName("Coil" , CoilFragment())


    )
}
