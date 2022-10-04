package playground.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import java.io.File


class CoilFragment : Fragment() {

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

        val ivFile: ImageView = view.findViewById(R.id.ivFile)
        val ivUrl: ImageView = view.findViewById(R.id.ivUrl)
        val ivDrawable: ImageView = view.findViewById(R.id.ivDrawable)

        // loading image into imageView through coil using different methods

        //Loading from a URL
        ivUrl.load("https://via.placeholder.com/600/92c952")

        //Loading from an image drawable
        ivDrawable.load(R.drawable.placeholder_img)

        // Loading from a file
        ivFile.load(File("/path/to/some_image_placeholder.png"))

        //adding placeholder to loading images
        ivUrl.load("https://via.placeholder.com/600/92c952") {
            placeholder(R.drawable.placeholder_img)
        }

        //applying some basic transformations to image using coil

        ivUrl.load("https://via.placeholder.com/600/92c952") {
            transformations(CircleCropTransformation()) // Crops and centres the image into a circle.
        }

        ivUrl.load("https://via.placeholder.com/600/92c952") {
            transformations(BlurTransformation(requireContext())) // Applies a Gaussian Blur.
        }

        ivUrl.load("https://via.placeholder.com/600/92c952") {
            transformations(GrayscaleTransformation()) // Shades the image into grayscale.
        }

        ivUrl.load("https://via.placeholder.com/600/92c952") {
            transformations(RoundedCornersTransformation()) //  Crops the image to fit the target's dimensions and rounds the corners of the image.
        }

    }

}
