package com.ort.guideapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.guideapp.R
import com.ort.guideapp.adapters.ActivityAdapter
import com.ort.guideapp.entities.Activity
import com.ort.guideapp.entities.ActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivitiesListFragment : Fragment() {

    companion object {
        fun newInstance() = ActivitiesListFragment()
    }

    /*___________________________________ attributes ___________________________________*/
    private lateinit var viewModel: ActivitiesListViewModel
    lateinit var v : View

    lateinit var recyclerActivity: RecyclerView
    lateinit var searchView: SearchView
    lateinit var adapterActivity: ActivityAdapter
    lateinit var activityRepository: ActivityRepository
    var activityList: MutableList<Activity>  = mutableListOf()

    /*___________________________________ onCreateView ___________________________________*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_activities_list, container, false)
        recyclerActivity = v.findViewById(R.id.recActivity)
        activityRepository = ActivityRepository()
        return v
    }

    /*___________________________________ onStart ___________________________________*/
    override fun onStart() {
        super.onStart()
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            activityList = activityRepository.getAllActivities()
            recyclerActivity.layoutManager = LinearLayoutManager(context)
            adapterActivity = ActivityAdapter(activityList){ position ->
                val action = ActivitiesListFragmentDirections.actionActivitiesListFragmentToActivityDetailFragment(activityList[position])
                findNavController().navigate(action)
            }
            recyclerActivity.adapter = adapterActivity
        }
        searchView = v.findViewById(R.id.searchView_activity)
    }

    /*___________________________________ onActivityCreated ___________________________________*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}