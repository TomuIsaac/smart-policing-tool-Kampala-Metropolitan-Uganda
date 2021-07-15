package com.thinkdevs.smartpolicing

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }

        if (Apps.pref.userType.equals("Police")){
            view.findViewById<LinearLayout>(R.id.top_main).visibility = View.VISIBLE
            view.findViewById<LinearLayout>(R.id.linear2).visibility = View.VISIBLE
            view.findViewById<CardView>(R.id.car_two).visibility = View.VISIBLE
        }else{
            view.findViewById<LinearLayout>(R.id.top_main).visibility = View.GONE
            view.findViewById<CardView>(R.id.car_two).visibility = View.GONE

        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.postDataFragment)
        }

        view.findViewById<CardView>(R.id.report_view).setOnClickListener {
            findNavController().navigate(R.id.reportFragment)
        }

        view.findViewById<CardView>(R.id.card_report).setOnClickListener {
            findNavController().navigate(R.id.postDataFragment)
        }
    }

}