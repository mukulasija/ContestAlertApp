package com.example.contestalert

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.lang.Exception
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext
import kotlin.properties.Delegates

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
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.starTime.text = "Start:    "+currentItem.StartTime.getDateWithServerTimeStamp()
        holder.endTime.text = "End  :    "+currentItem.EndTime.getDateWithServerTimeStamp()
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

    @SuppressLint("NewApi")
    private fun String.getDateWithServerTimeStamp(): String {
        val string = this
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        if(site=="code_chef")
        {
            formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'",Locale.ENGLISH)
        }
        val datetime = LocalDateTime.parse(string,formatter)
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")
        return datetime!!.format(format)
    }

    }
class ContestViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val titleView : TextView = itemView.findViewById(R.id.name)
    val starTime : TextView = itemView.findViewById(R.id.starttime)
    val endTime : TextView = itemView.findViewById(R.id.endtime)
    val logo : ImageView = itemView.findViewById(R.id.logo)
    val card : CardView = itemView.findViewById(R.id.card)
//    val titleView : TextView = itemView.findViewById(R.id.title)
//    val author : TextView = itemView.findViewById(R.id.author)
//    val image : ImageView = itemView.findViewById(R.id.thumbnail)
}
interface ContestClicked{
    fun onItemClicked(item : Contest)
}