package com.example.contestalert

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import java.util.*


class ContestList(public val site: String, public val siteName: String) :
    Fragment(R.layout.fragment_contest_list), ContestClicked {
    private lateinit var mAdapter: ContestListAdapter
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
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        fetchData()
        mAdapter = ContestListAdapter(this, site)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val url = "https://kontests.net/api/v1/" + site
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                { response ->
                    val ContestJsonArray = response
                    val ContestArray = ArrayList<Contest>()
                    for (i in ContestJsonArray.length() - 1 downTo 0) {
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
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            )
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(requireView().context).addToRequestQueue(jsonObjectRequest)
        } else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                ActivityCompat.finishAffinity(requireActivity())
            }
            dialog.setNegativeButton("Exit App") { text, listener ->
                ActivityCompat.finishAffinity(requireActivity())
            }
            dialog.create()
            dialog.show()
        }

    }

    override fun reminderClicked(item: Contest) {
        val startMillis = item.StartTime.getDateWithServerTimeStamp(site).toDate(
            "dd-MM-yyyy | hh:mm a",
            TimeZone.getDefault()
        ).time
        val endMillis = item.EndTime.getDateWithServerTimeStamp(site).toDate(
            "dd-MM-yyyy | hh:mm a",
            TimeZone.getDefault()
        ).time
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            .putExtra(CalendarContract.Events.TITLE, siteName + " Contest")
            .putExtra(CalendarContract.Events.DESCRIPTION, item.title)
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        startActivity(intent)
    }
}
