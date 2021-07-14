package com.thinkdevs.smartpolicing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thinkdevs.smartpolicing.util.UserData


class SignupFragment : Fragment() {

    lateinit var signUp: Button
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var c_password: EditText
    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var userData: UserData
    private lateinit var auth: FirebaseAuth
//    lateinit var profilePic: CircleImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        userData = UserData(requireContext())
        auth = FirebaseAuth.getInstance()
        signUp = view.findViewById<Button>(R.id.btn_signup)
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("User Data")

        name = view.findViewById(R.id.txt_input_name)
        email = view.findViewById(R.id.txt_input_email)
        password = view.findViewById(R.id.txt_input_password)
        c_password = view.findViewById(R.id.txt_input_confirm_password)

        signUp.setOnClickListener {
            val nam = name.text.toString()
            val mail = email.text.toString()
            val pass = password.text.toString()
            val cPass = c_password.text.toString()
            when {
                nam.isEmpty() -> name.error = "Enter a Name"
                mail.isEmpty() -> email.error = "Enter a Email"
                pass.isEmpty() -> password.error = "Enter a Password"
                cPass.isEmpty() -> c_password.error = "Enter Password again"
                pass != cPass -> c_password.error = "Password miss match"
                else -> signUp(it, nam, mail, pass)
            }
        }
        return view
    }


    private fun signUp(view: View, name: String, mail: String, pass: String) {
        signUp.visibility = View.GONE
        auth.createUserWithEmailAndPassword(mail, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                   // userData.session = findViewById<CheckBox>(R.id.remember).isChecked
                    val user = auth.currentUser
                    database.getReference("User Index").child(user!!.uid).setValue(name)
                    myRef.child(user.uid).child("Followers").setValue(0)
                    myRef.child(user.uid).child("Following").setValue(0)
                    myRef.child(user.uid).child("Email").setValue(mail)
                    myRef.child(user.uid).child("Name").setValue(name)
                    userData.name = name
                    userData.email = mail
                    userData.uid = user.uid
                   // userData.image = ConstantConfig().image
                   // myRef.child(user.uid).child("Photo").setValue(ConstantConfig().image)
                    findNavController().navigate(R.id.FirstFragment)

                } else {
                    try {
                        //Toast.makeText(this@SignupActivity, it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        Snackbar.make(view, it.exception!!.message.toString(), Snackbar.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.println(Log.ASSERT,"Ex","$e")
                    }
                   // updateUIF(null)
                }
                signUp.visibility = View.VISIBLE
            }
    }


}