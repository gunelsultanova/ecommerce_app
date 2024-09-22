package com.matrix.android105_android.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.matrix.android105_android.R
import com.matrix.android105_android.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint



class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signIn()
    }

    fun signIn() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextUserName.text.toString()
            val password = binding.editTextUserPassword.text.toString()

            when {
                email.isEmpty() -> Toast.makeText(
                    requireContext(),
                    "Email is empty",
                    Toast.LENGTH_SHORT
                ).show()

                password.isEmpty() -> Toast.makeText(
                    requireContext(),
                    "Password is empty",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {

                    Log.d("LoginFragment", "Attempting sign-in with email: $email")
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener() { task ->

                            if (task.isSuccessful) {
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                                Toast.makeText(
//                                    requireContext(),
//                                    "Sign in is successful",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            }
                            else {
                                Toast.makeText(
                                    requireContext(),
                                    "Email or password is wrong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .addOnFailureListener {
                            Log.d("FirebaseAuth", "Error signing in")
                        }
                }
            }
        }
    }
}