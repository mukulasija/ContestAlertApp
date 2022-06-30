package com.example.contestalert

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class ContestListAdapter(private val listener : ContestClicked,public val site : String) : RecyclerView.Adapter<ContestViewHolder>() {
    private val items : ArrayList<Contest> = ArrayList()
    public val logo  = getlogo()
    public var color = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest,parent,false)
        val viewHolder = ContestViewHolder(view)
       color = getcolor(parent)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        val reminder = view.findViewById<TextView>(R.id.reminder)
        reminder.setOnClickListener {
            listener.reminderClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val currentItem = items[position]
        val startMillis = currentItem.StartTime.getDateWithServerTimeStamp(site).toDate("dd-MM-yyyy | hh:mm a",
            TimeZone.getDefault()).time
        val endMillis = currentItem.EndTime.getDateWithServerTimeStamp(site).toDate("dd-MM-yyyy | hh:mm a",
            TimeZone.getDefault()).time
        val diff = endMillis-startMillis
        val seconds : Double= (diff / 1000).toDouble()
        val minutes : Double= seconds / 60
        val hours : Double = minutes / 60
        val days : Double= hours / 24
        val years : Double = days/365
        var duration : String? = null
        if(days>1)
           duration = days.toInt().toString()+" Days"
        else
            if(hours>1)
                duration = hours.toString()+" Hours"
        else
            if(minutes>1)
                duration = minutes.toString()+" Minutes"
        holder.duration.text = duration
        holder.titleView.text = currentItem.title
        holder.starTime.text = currentItem.StartTime.getDateWithServerTimeStamp(site)
        holder.endTime.text = currentItem.EndTime.getDateWithServerTimeStamp(site)
        holder.logo.setImageResource(logo)
        holder.card.setCardBackgroundColor(color)
    }

    private fun getcolor(parent: ViewGroup): Int {
        if(site.equals("code_chef"))
        {
            return ContextCompat.getColor(parent.context,R.color.codechef)

        }
        if(site.equals("codeforces"))
        {
            return ContextCompat.getColor(parent.context,R.color.codeforces)


        }
        if(site.equals("leet_code"))
        {
            return ContextCompat.getColor(parent.context,R.color.leetcode)

        }
        return Color.parseColor("#BCBCBC")
    }

    private fun getlogo(): Int {
        if(site.equals("code_chef"))
        {
            color= Color.parseColor("#BCBCBC")
            return R.drawable.codechef1
        }
        if(site.equals("codeforces"))
        {
            color= Color.parseColor("#A97C00")
            return  R.drawable.codeforces

        }
        if(site.equals("leet_code"))
        {
            color= Color.parseColor("#FF3131")
            return R.drawable.leet_codenew

        }
        return R.drawable.codeforces
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

    @RequiresApi(Build.VERSION_CODES.O)
    public fun convert(date : Date) : LocalDate{
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
    }
class ContestViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val titleView : TextView = itemView.findViewById(R.id.name)
    val starTime : TextView = itemView.findViewById(R.id.date_start)
    val endTime : TextView = itemView.findViewById(R.id.date_end)
    val logo : ImageView = itemView.findViewById(R.id.logo)
    val card : CardView = itemView.findViewById(R.id.card)
    val duration : TextView = itemView.findViewById(R.id.duration)
//    val titleView : TextView = itemView.findViewById(R.id.title)
//    val author : TextView = itemView.findViewById(R.id.author)
//    val image : ImageView = itemView.findViewById(R.id.thumbnail)
}
interface ContestClicked{
    fun onItemClicked(item : Contest)
    fun reminderClicked(item: Contest)
}