package com.example.contestalert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.contestalert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ContestClicked {
    private lateinit var mAdapter: ContestListAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        fetchData()
        mAdapter=ContestListAdapter(this)

        binding.recyclerView.adapter = mAdapter

    }

    private fun fetchData()
    {
      val url = "https://kontests.net/api/v1/codeforces"
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
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
                Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
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
        Toast.makeText(this, "clicked on ${item.title}",Toast.LENGTH_LONG).show()
    }


}