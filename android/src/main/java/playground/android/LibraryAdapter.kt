package playground.android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView



class LibraryAdapter(private val context: Context?, private val libraries: List<LibraryName>):RecyclerView.Adapter<LibraryAdapter.MyViewHolder>() {
   inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.name_txt)
        fun setData(library: LibraryName, position: Int) {
            name.text = library.name
        }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
       return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val library = libraries[position]
       holder.setData(library, position)
        holder.itemView.setOnClickListener {

            val activity = context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().replace(R.id.main_fragment, library.fragment).addToBackStack(null).commit()
        }
    }






    override fun getItemCount(): Int {
        return libraries.size
    }
}

