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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thinkdevs.smartpolicing.util.UserData




class SignupFragment : Fragment() {

    lateinit var signUp: Button
    lateinit var name: EditText
    lateinit var police_number: EditText
    lateinit var police: LinearLayout
    lateinit var community: LinearLayout
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var c_password: EditText
    lateinit var myRef: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var userData: UserData
    private lateinit var auth: FirebaseAuth
    lateinit var usertype:String
//    lateinit var profilePic: CircleImageView

    var users =
        arrayOf("Community", "Police")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        userData = UserData(requireContext())
        auth = FirebaseAuth.getInstance()
        signUp = view.findViewById(R.id.btn_signup)
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("User Data")

        name = view.findViewById(R.id.txt_input_name)
        police = view.findViewById(R.id.policy)
        community = view.findViewById(R.id.email)
        email = view.findViewById(R.id.txt_input_email)
        password = view.findViewById(R.id.txt_input_password)
        police_number = view.findViewById(R.id.txt_input_policy_number)
        c_password = view.findViewById(R.id.txt_input_confirm_password)
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



        signUp.setOnClickListener {
            val nam = name.text.toString()
            val mail = email.text.toString()
            val pass = password.text.toString()
            val cPass = c_password.text.toString()

            if (usertype == "Police") {
                signUp(it, nam, police_number.text.toString()+"@gmail.com", pass, police_number.text.toString())
            }else{
                signUp(it, nam, mail, pass, police_number.text.toString())

            }
//            when {
//                nam.isEmpty() -> name.error = "Enter a Name"
//                mail.isEmpty() -> email.error = "Enter a Email"
//                pass.isEmpty() -> password.error = "Enter a Password"
//                cPass.isEmpty() -> c_password.error = "Enter Password again"
//                pass != cPass -> c_password.error = "Password miss match"
//                else -> signUp(it, nam, mail, pass,police_number.text.toString())
//            }
        }
        return view
    }


    private fun signUp(view: View, name: String, mainsender: String, pass: String, policeNuber: String) {
        signUp.visibility = View.GONE
        val pDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading Please wait!..."
        pDialog.setCancelable(false)
        pDialog.show()
        auth.createUserWithEmailAndPassword(mainsender, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    pDialog.dismiss()

                    // userData.session = findViewById<CheckBox>(R.id.remember).isChecked
                    val user = auth.currentUser
                    database.getReference("User Index").child(user!!.uid).setValue(name)
                    myRef.child(user.uid).child("userType").setValue(usertype)
                    myRef.child(user.uid).child("Email").setValue(mainsender)
                    myRef.child(user.uid).child("Name").setValue(name)
                    myRef.child(user.uid).child("Police_number").setValue(policeNuber)
                    userData.name = name
                    userData.email = mainsender
                    userData.uid = user.uid
                   // userData.image = ConstantConfig().image
                   // myRef.child(user.uid).child("Photo").setValue(ConstantConfig().image)
                    Apps.pref.userType = usertype!!
                    Apps.pref.islogin = true
                    SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("success...")
                        .setContentText("successful")
                        .show()
                    findNavController().navigate(R.id.SecondFragment)

                } else {
                    try {
                        pDialog.dismiss()
                        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(it.exception!!.message)
                            .show()
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