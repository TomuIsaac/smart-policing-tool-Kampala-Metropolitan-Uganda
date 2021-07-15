package com.thinkdevs.smartpolicing

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.thinkdevs.smartpolicing.model.PostData


class PostDataFragment : Fragment() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    lateinit var name:AppCompatEditText
    lateinit var phone:AppCompatEditText
    lateinit var message:AppCompatEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_data, container, false)

         name = view.findViewById(R.id.txt_input_name)
        var email = view.findViewById<AppCompatEditText>(R.id.txt_input_email)
         message = view.findViewById(R.id.txt_input_message)
         phone = view.findViewById<AppCompatEditText>(R.id.txt_input_phone_number)

        view.findViewById<Button>(R.id.btn_report).setOnClickListener {

            var fullname = name.text.toString()
            var Fmessage = message.text.toString()
            var phoneNumber = phone.text.toString()
            var emailD = phone.text.toString()
            when {
                fullname.isEmpty() -> name.error = "Enter a Name"
                Fmessage.isEmpty() -> email.error = "Enter a Message"
                phoneNumber.isEmpty() -> phone.error = "Enter a Phone Number"
               // emailD.isEmpty() -> email.error = "Enter Email Address"

                else -> {

                    submitDataBarang(fullname, Fmessage, phoneNumber)
                }

            }

        }

        return view
    }

    private fun submitDataBarang(
        fullname: String,
        Fmessage: String,
        phoneNumber: String) {
        val pDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Please wait!..."
        pDialog.setCancelable(false)
        pDialog.show()
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance!!.getReference("smartPolicing");

        val postData = PostData(fullname, Fmessage, phoneNumber)
        log("data posting " + postData)
        mDatabaseReference!!.child("community").push()
            .setValue(postData)
            .addOnSuccessListener(requireActivity()
            ) {
                pDialog.dismiss()
                name.text!!.clear()
                phone.text!!.clear()
                message.text!!.clear()
                SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("success")
                    .setContentText("We received your Message, We are getting back to you")
                    .show()
            }
            .addOnFailureListener {
                pDialog.dismiss()
                log("On FAILURE " + it.message)
                SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("failure")
                    .setContentText(it.message)
                    .show()
            }
    }

    private fun getDataFromFcm() {
        val database = FirebaseDatabase.getInstance().getReference()
        val usersRef: DatabaseReference = database.child("from_community")
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list: MutableList<String?> = ArrayList()
                for (ds in dataSnapshot.children) {
                    val uid = ds.key
                    list.add(uid)
                }

                //Do what you need to do with your list
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message) //Don't ignore errors!
            }
        }
        usersRef.addListenerForSingleValueEvent(valueEventListener)
    }

    fun writeNewUser(
        userId: String,
        name: String,
        message: String,
        email: String,
        phoneNumber: String
    ) {
        val user = PostData(name, message, phoneNumber)

//        database.child("from_community").child(userId).setValue(user)
    }

    private fun log(msg: String) {
        Log.e(javaClass.simpleName, msg)
    }

}