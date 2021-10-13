package com.example.contestalert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ContestListAdapter(private val listener : ContestClicked) : RecyclerView.Adapter<ContestViewHolder>() {
    private val items : ArrayList<Contest> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest,parent,false)
        val viewHolder = ContestViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.starTime.text = "Start Time : "+currentItem.StartTime
        holder.endTime.text = "End Time : "+currentItem.EndTime
    }

    override fun getItemCount(): Int {
     return items.size
    }
    fun updateList(updatedList : ArrayList<Contest>)
    {
        items.clear()
        items.addAll(updatedList)
        notifyDataSetChanged()
    }
}
class ContestViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val titleView : TextView = itemView.findViewById(R.id.name)
    val starTime : TextView = itemView.findViewById(R.id.starttime)
    val endTime : TextView = itemView.findViewById(R.id.endtime)
//    val titleView : TextView = itemView.findViewById(R.id.title)
//    val author : TextView = itemView.findViewById(R.id.author)
//    val image : ImageView = itemView.findViewById(R.id.thumbnail)
}
interface ContestClicked{
    fun onItemClicked(item : Contest)
}