package playground.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import java.io.File


class CoilFragment : Fragment() {

    private var ivFile: ImageView? = null
    private var ivUrl: ImageView? = null
    private var ivDrawable: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initializing the imageViews
        initializeImages()

        //Loading from a URL
        loadFromURL()

        //Loading from an image drawable
        loadFromDrawable()

        // Loading from a file
        loadFromFile()

        //adding placeholder to loading images
        addingPlaceholder()

        //applying some basic transformations to image using coil

        // Crops and centres the image into a circle.
        circleCropTransformation()

        //  Crops the image to fit the target's dimensions and rounds the corners of the image.
        roundedCornerTransformation()


    }

    private fun roundedCornerTransformation() {
        ivUrl?.load("https://via.placeholder.com/600/92c952") {
            transformations(RoundedCornersTransformation())
        }
    }

    private fun circleCropTransformation() {
        ivUrl?.load("https://via.placeholder.com/600/92c952") {
            transformations(CircleCropTransformation())
        }
    }

    private fun addingPlaceholder() {
        ivUrl?.load("https://via.placeholder.com/600/92c952") {
            placeholder(R.drawable.placeholder_img)
        }
    }

    private fun loadFromFile() {
        ivFile?.load(File("/path/to/some_image_placeholder.png"))
    }

    private fun loadFromDrawable() {
        ivDrawable?.load(R.drawable.placeholder_img)
    }

    private fun loadFromURL() {
        ivUrl?.load("https://via.placeholder.com/600/92c952")
    }

    private fun initializeImages() {
         ivFile = view?.findViewById(R.id.ivFile)
         ivUrl = view?.findViewById(R.id.ivUrl)
         ivDrawable = view?.findViewById(R.id.ivDrawable)
    }

}
