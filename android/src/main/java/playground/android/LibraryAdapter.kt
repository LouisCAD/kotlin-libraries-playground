package playground.android

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class LibraryAdapter(private val context:Context, private val libraries:List<LibraryName>):RecyclerView.Adapter<LibraryAdapter.MyViewHolder>() {
   inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.name_txt)

        fun setData(library: LibraryName, position: Int) {
            name.text = library.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false)
       return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val library = libraries[position]
       holder.setData(library,position)
        holder.itemView.setOnClickListener { listener(library) }
    }

    private fun listener(item: LibraryName) {

        var intent = Intent()

        when(item.name)
        {
            "Contour" -> {
                intent = Intent(context,ContourSample::class.java)
            }

            "Coil" ->{
                intent = Intent(context,CoilSample::class.java)
            }
        }

          context.startActivity(intent)

    }

    override fun getItemCount(): Int {
        return libraries.size
    }
}
