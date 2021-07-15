package com.thinkdevs.smartpolicing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.thinkdevs.smartpolicing.adapter.AdapterListBasic
import com.thinkdevs.smartpolicing.model.PostData
import com.thinkdevs.smartpolicing.util.getPeopleData


class ReportFragment : Fragment() {

    private var parent_view: View? = null

    private var recyclerView: RecyclerView? = null
    private var mAdapter: AdapterListBasic? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    private var values: MutableList<PostData>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        parent_view = view.findViewById(android.R.id.content)
        getDataFromFcm()
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        recyclerView!!.setHasFixedSize(true)
        values = ArrayList()

//        val items: MutableList<PostData> = getPeopleData(requireContext())
//        items.addAll(getPeopleData(requireContext()))
        //set data and list adapter
        Log.e("TAG", "RESPONSE FROM DATABASE " +getDataFromFcm())




        return view
    }
    private fun getDataFromFcm(): MutableCollection<PostData>? {

        val database = FirebaseDatabase.getInstance().reference
        val usersRef: DatabaseReference = database.child("smartPolicing").child("community")
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    println(it.toString())
                    val ccc = it.getValue(PostData::class.java)
                    Log.e("TAG", "--------- " +ccc)

                    values!!.add(ccc!!)
                }
                if (values!!.count() == 0){
                    //nothing
                }else{
                    mAdapter = AdapterListBasic(requireContext(), values!!)
                    recyclerView!!.adapter = mAdapter
                }


                Log.e("TAG", "-----|||||||---- " +values)
            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message) //Don't ignore errors!
            }
        }
        usersRef.addListenerForSingleValueEvent(valueEventListener)
        return values

    }

}