package com.thinkdevs.smartpolicing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thinkdevs.smartpolicing.util.DataReader
import com.thinkdevs.smartpolicing.util.Loaded
import com.thinkdevs.smartpolicing.util.UserData
import java.lang.Exception

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private val RC_SIGN_IN = 9001

    lateinit var signButton: Button
    lateinit var userData: UserData
    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<EditText>(R.id.txt_input_email)
        var password = view.findViewById<EditText>(R.id.txt_input_password)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("User Data")

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val mail = email.text.toString()
            val pass = password.text.toString()
            when {
                mail.isEmpty() -> email.error = "Email Required"
                pass.isEmpty() -> password.error = "Password Required"
                else -> firebaseLogin(mail, pass)
            }
        }
        view.findViewById<TextView>(R.id.sign_up).setOnClickListener {
            findNavController().navigate(R.id.signupFragment)

        }
    }

    private fun firebaseLogin(mail: String, pass: String) {
       // signButton.visibility = View.GONE
        Log.println(Log.ASSERT, "email ", " password " + mail + "Paa " +pass)
        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.println(Log.ASSERT,"Firebase", "Authentication Success.")
                findNavController().navigate(R.id.SecondFragment)
                DataReader(object: Loaded {
                    override fun onLoaded(x: Int, data: DataSnapshot) {
                       // userData.image = data.child("Photo").value.toString()
                        userData.name = data.child("Name").value.toString()
                        userData.email = data.child("Email").value.toString()
//                        val intent = Intent(requireActivity(), DashboardFragment::class.java)
//                        startActivity(intent)
//                        requireActivity().finish()
                        Log.println(Log.ASSERT,"Firebase", "INIDE THE APP")

                    }

                }).listenOnce(myRef.child(auth.currentUser!!.uid))
            } else {
              //  signButton.visibility = View.VISIBLE
                Log.println(Log.ASSERT,"Firebase", "Authentication Failed.")
//                updateUIF(null)
            }
            try {
                Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) { Log.println(Log.ASSERT, "Ex Login", "$e") }
        }
    }
}