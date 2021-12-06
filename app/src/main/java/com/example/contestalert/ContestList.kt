package com.example.contestalert

import android.os.Bundle
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


class ContestList(public val site : String) : Fragment(R.layout.fragment_contest_list), ContestClicked {

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
            Response.Listener { response ->
                val ContestJsonArray = response
                val ContestArray = ArrayList<Contest>()
                for(i in 0 until ContestJsonArray.length())
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
            Response.ErrorListener { error ->
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

}