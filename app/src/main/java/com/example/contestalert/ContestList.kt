package com.example.contestalert

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.milliseconds


class ContestList(public val site : String,public val siteName : String) : Fragment(R.layout.fragment_contest_list), ContestClicked {
    private lateinit var mAdapter : ContestListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(view.context)
        fetchData()
        mAdapter=ContestListAdapter(this,site)
        recyclerView.adapter = mAdapter
    }
    private fun fetchData()
    {
        val url = "https://kontests.net/api/v1/"+site
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val ContestJsonArray = response
                val ContestArray = ArrayList<Contest>()
                for(i in ContestJsonArray.length()-1 downTo 0)
                {
                    val ContestJsonObject = ContestJsonArray.getJSONObject(i)
                    val contest = Contest(
                        ContestJsonObject.getString("name"),
                        ContestJsonObject.getString("start_time"),
                        ContestJsonObject.getString("end_time")
                    )
                    ContestArray.add(contest)
                }
                mAdapter.updateList(ContestArray)
            },
            { error ->
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
//                Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
            }
        )
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(requireView().context).addToRequestQueue(jsonObjectRequest)
//        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
//            {
//
////                val ContestJsonArray = it.getJSONArray()
//                val ContestArray = ArrayList<Contest>()
//                val ContestJsonArray = it.getJSONArray("")
//
//                for(i in 0 until ContestJsonArray.length())
//                {
//                    val ContestJsonObject = ContestJsonArray.getJSONObject(i)
//                    val contest = Contest(
//                        ContestJsonObject.getString("name"),
//                        ContestJsonObject.getString("start_time")
//                    )
//                   ContestArray.add(contest)
//                }
//                mAdapter.updateList(ContestArray)
//            },
//            {
//            }
//        )
//
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(item: Contest) {
       Toast.makeText(context,item.title+" clicked",Toast.LENGTH_SHORT).show()
    }


    override fun reminderClicked(item: Contest) {

//        Toast.makeText(context,item.StartTime,Toast.LENGTH_SHORT).show()
        val startMillis = item.StartTime.getDateWithServerTimeStamp(site).toDate("dd-MM-yyyy | hh:mm a",
            TimeZone.getDefault()).time
        val endMillis = item.EndTime.getDateWithServerTimeStamp(site).toDate("dd-MM-yyyy | hh:mm a",
            TimeZone.getDefault()).time
//        Toast.makeText(context,strt.toString(),Toast.LENGTH_SHORT).show();
//        val startMillis: Long = Calendar.getInstance().run {
//            set(2012, 0, 19, 7, 30)
//            timeInMillis
//        }
//        val endMillis: Long = Calendar.getInstance().run {
//            set(2012, 0, 19, 8, 30)
//            timeInMillis
//        }
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE,siteName + " Contest")
            .putExtra(CalendarContract.Events.DESCRIPTION, item.title)
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        startActivity(intent)
    }
}