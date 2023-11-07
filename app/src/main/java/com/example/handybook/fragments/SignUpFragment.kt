package com.example.handybook.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.handybook.R
import com.example.handybook.databinding.FragmentSignUpBinding
import com.example.handybook.models.UserReg
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentSignUpBinding.inflate(inflater,container,false)
        binding.backBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, SignInFragment())
                .commit()
        }
        val name:String=binding.name.toString()
        val second_name:String=binding.surname.text.toString()
        val email:String=binding.email.text.toString()
        val parol:String=binding.password.text.toString()
        val api = APIClient.getInstance().create(APIService::class.java)
        binding.signUpBtn.setOnClickListener {
            if (check_info()) {
                val registerInfo = UserReg(name, second_name, email, parol)
                api.register(registerInfo).enqueue(object : Callback<UserReg> {
                    override fun onResponse(call: Call<UserReg>, response: Response<UserReg>) {
                        if (response.code() == 201) {
                            Toast.makeText(context, "Registration success!", Toast.LENGTH_SHORT)
                                .show()
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main, HomeFragment())
                                .commit()
                        }
                    }

                    override fun onFailure(call: Call<UserReg>, t: Throwable) {
                        Log.d(ContentValues.TAG, "onFailure: $t")
                        Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
        return binding.root
    }
    fun check_info(): Boolean {
        if (binding.password.text.toString()!=(binding.againPassword.text.toString())) {
            Toast.makeText(requireContext(),"Parollar mos kelmadi",Toast.LENGTH_SHORT).show()
            binding.password.error="Parollar mos kelmadi"
            binding.againPassword.error="Parollar mos kelmadi"
            return false
        }
        if (binding.password.text!!.count()<8 && binding.againPassword.text!!.count()<8){
            Toast.makeText(requireContext(),"Parol 8 ta belgidan kam bolmasligi kerak",Toast.LENGTH_SHORT).show()
            binding.password.error="Parol 8 ta belgidan kam bolmasligi kerak"
            binding.againPassword.error="Parol 8 ta belgidan kam bolmasligi kerak"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()){
            Toast.makeText(requireContext(),"Email hato",Toast.LENGTH_SHORT).show()
            binding.email.error="Emailni to'g'ri kiriting"
            return false
        }
        if(binding.name==null){
            Toast.makeText(requireContext(),"Ismingizni kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.password==null){
            Toast.makeText(requireContext(),"Parol kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.againPassword==null){
            Toast.makeText(requireContext(),"Parolni tasdiqlash uchun yana bir bor kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.email==null){
            Toast.makeText(requireContext(),"Email manzilingizni kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.surname==null){
            Toast.makeText(requireContext(),"Familiyangizni kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.name==null && binding.surname==null && binding.password==null && binding.againPassword==null && binding.email==null){
            Toast.makeText(requireContext(),"Ismingizni kiriting",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}