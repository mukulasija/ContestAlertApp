package com.example.contestalert

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

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
        holder.starTime.text = "Start:    "+currentItem.StartTime.getDateWithServerTimeStamp()
        holder.endTime.text = "End  :    "+currentItem.EndTime.getDateWithServerTimeStamp()


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
//    String string = "2021-10-15T15:05:00.000Z";
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//    LocalDate date = LocalDate.parse(string, formatter);
//    LocalDateTime datetime = LocalDateTime.parse(string,formatter);
//    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
//    datetime.format(format)

    @SuppressLint("NewApi")
    private fun String.getDateWithServerTimeStamp(): String {
        val string = this
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val datetime = LocalDateTime.parse(string,formatter)
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")
        return datetime.format(format)
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