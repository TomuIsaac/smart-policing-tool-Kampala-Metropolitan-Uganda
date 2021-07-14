package com.thinkdevs.smartpolicing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thinkdevs.smartpolicing.adapter.AdapterListBasic
import com.thinkdevs.smartpolicing.model.PostData
import com.thinkdevs.smartpolicing.util.DataGenerator
import com.thinkdevs.smartpolicing.util.getPeopleData


class ReportFragment : Fragment() {

    private var parent_view: View? = null

    private var recyclerView: RecyclerView? = null
    private var mAdapter: AdapterListBasic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        parent_view = view.findViewById(android.R.id.content)
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(requireContext()))
        recyclerView!!.setHasFixedSize(true)

        val items: MutableList<PostData> = getPeopleData(requireContext())
        items.addAll(getPeopleData(requireContext()))
        //set data and list adapter

        //set data and list adapter
        mAdapter = AdapterListBasic(requireContext(), items)
        recyclerView!!.adapter = mAdapter

        return view
    }

}