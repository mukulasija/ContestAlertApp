package com.example.contestalert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.contestalert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: ContestListAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        binding.recyclerView.layoutManager= LinearLayoutManager(this)
//        fetchData()
//        mAdapter=ContestListAdapter(this)
//
//        binding.recyclerView.adapter = mAdapter
        val secondFragment = ContestList("codeforces","Codeforces")
        val firstFragment = ContestList("code_chef","CodeChef")
        val thirdFragment = ContestList("leet_code","LeetCode")
        setCurrentFragment(firstFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.codeforces->setCurrentFragment(secondFragment)
                R.id.codechef->setCurrentFragment(firstFragment)
                R.id.leetcode->setCurrentFragment(thirdFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}