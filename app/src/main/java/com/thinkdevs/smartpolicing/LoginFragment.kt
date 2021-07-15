package com.thinkdevs.smartpolicing

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thinkdevs.smartpolicing.util.DataReader
import com.thinkdevs.smartpolicing.util.Loaded
import com.thinkdevs.smartpolicing.util.UserData


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {
    private val RC_SIGN_IN = 9001

    lateinit var signButton: Button
    lateinit var userData: UserData
    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var usertype:String
    lateinit var police: LinearLayout
    lateinit var community: LinearLayout
    private lateinit var auth: FirebaseAuth
    lateinit var police_number: EditText

    var users =
        arrayOf("Community", "Police")


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Apps.pref.islogin){
            findNavController().navigate(R.id.SecondFragment)
        }
        police = view.findViewById(R.id.policy_linear)
        police_number = view.findViewById(R.id.txt_input_policy_number)

        community = view.findViewById(R.id.email)
        val spin = view.findViewById(R.id.user_type) as AppCompatSpinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, parent: View?, position: Int, p3: Long) {
                usertype = p0!!.getItemAtPosition(position) as String
                Log.println(Log.ASSERT,"user type","$usertype")
                if(usertype.equals("Police")){
                    community.visibility = View.GONE
                    police.visibility = View.VISIBLE
                }else{
                    community.visibility = View.VISIBLE
                    police.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        val email = view.findViewById<EditText>(R.id.txt_input_email)
        val password = view.findViewById<EditText>(R.id.txt_input_password)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("User Data")
        userData= UserData(requireContext())
        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val mail = email.text.toString()
            val pass = password.text.toString()
            when {
//                if (usertype.equals("Police")){
//                    mail.isEmpty() -> email.error = "Email Required"
//                    pass.isEmpty() -> password.error = "Password Required"
//                }else{
//
//                }
                else -> {
                    if (usertype.equals("Police")){
                        firebaseLogin(police_number.text.toString()+"@gmail.com", pass)
                    }else{
                        firebaseLogin(mail, pass)
                    }
                }
            }
        }
        view.findViewById<TextView>(R.id.sign_up).setOnClickListener {
            findNavController().navigate(R.id.signupFragment)

        }
    }

    private fun firebaseLogin(mail: String, pass: String) {
        val pDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading Please wait!..."
        pDialog.setCancelable(false)
        pDialog.show()
        Log.println(Log.ASSERT, "email ", " password " + mail + "Paa " +pass)
        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                pDialog.dismiss()
                Log.println(Log.ASSERT,"Firebase", "Authentication Success." +task.result)
                Apps.pref.islogin = true
                findNavController().navigate(R.id.SecondFragment)
                DataReader(object: Loaded {
                    override fun onLoaded(x: Int, data: DataSnapshot) {
                       // userData.image = data.child("Photo").value.toString()
                        Apps.pref.userType = data.child("userType").value.toString()
                        Apps.pref.userName = data.child("Name").value.toString()
                        Apps.pref.userPhone = data.child("Phone").value.toString()
//                        val intent = Intent(requireActivity(), DashboardFragment::class.java)
//                        startActivity(intent)
//                        requireActivity().finish()
                        Log.println(Log.ASSERT,"Firebase", "INIDE THE APP")

                    }

                }).listenOnce(myRef.child(auth.currentUser!!.uid))
            } else {
                pDialog.dismiss()
                try {
                    Apps.pref.islogin = false

                    pDialog.dismiss()
                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(task.exception!!.message)
                        .show()
                    Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT).show()
                } catch (e: Exception) { Log.println(Log.ASSERT, "Ex Login", "$e") }
              //  signButton.visibility = View.VISIBLE
                Log.println(Log.ASSERT,"Firebase",
                    "Authentication Failed." +task.exception!!.message)

                println("school ------- " +task.exception!!.localizedMessage)
//                updateUIF(null)
            }

        }
    }
}