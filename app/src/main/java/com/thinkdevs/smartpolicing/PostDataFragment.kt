package com.thinkdevs.smartpolicing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.thinkdevs.smartpolicing.model.PostData


class PostDataFragment : Fragment() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mFirebaseInstance: FirebaseDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_data, container, false)

        var name = view.findViewById<AppCompatEditText>(R.id.txt_input_name)
        var email = view.findViewById<AppCompatEditText>(R.id.txt_input_email)
        var message = view.findViewById<AppCompatEditText>(R.id.txt_input_message)
        var phone = view.findViewById<AppCompatEditText>(R.id.txt_input_phone_number)

        view.findViewById<Button>(R.id.btn_report).setOnClickListener {

            var fullname = name.text.toString()
            var Fmessage = message.text.toString()
            var phoneNumber = phone.text.toString()
            var emailD = phone.text.toString()
            when {
                fullname.isEmpty() -> name.error = "Enter a Name"
                Fmessage.isEmpty() -> email.error = "Enter a Message"
                phoneNumber.isEmpty() -> phone.error = "Enter a Phone Number"
                emailD.isEmpty() -> email.error = "Enter Email Address"

                else -> submitDataBarang(fullname, Fmessage, phoneNumber, emailD)
            }

        }

        return view
    }

    private fun submitDataBarang(
        fullname: String,
        Fmessage: String,
        phoneNumber: String,
        emailD: String
    ) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance!!.getReference("smartPolicing");

        val postData = PostData(fullname, Fmessage, phoneNumber, emailD)
        log("data posting " + postData)
        mDatabaseReference!!.child("community").push()
            .setValue(postData)
            .addOnSuccessListener(requireActivity()
            ) {
                Toast.makeText(
                    requireContext(),
                    "S !",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                log("On FAILURE " + it.message)
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
        val user = PostData(name, message, email, phoneNumber)

//        database.child("from_community").child(userId).setValue(user)
    }

    private fun log(msg: String) {
        Log.e(javaClass.simpleName, msg)
    }

}