package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fooddelivery.databinding.FragmentProfileBinding
import com.example.fooddelivery.databinding.FragmentSearchBinding
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val auth = Firebase.auth
    private val db = Firebase.database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.update.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.login.text.toString().trim()
            val password = binding.password.text.toString().trim()
            updateProfileUser(auth, name, email, password)
        }
        binding.delete.setOnClickListener{
            val email = binding.login.text.toString().trim()
            val password = binding.password.text.toString().trim()
            deleteUser(auth, email, password)
        }
        binding.signOut.setOnClickListener{
            signOut(auth)
        }
        return binding.root
    }


    private fun updateProfileUser(
        auth: FirebaseAuth,
        name: String,
        email: String,
        password: String
    ) {

        val user = auth.currentUser

        if (user != null) {
            // Обновление имени пользователя
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }


//            user.verifyBeforeUpdateEmail(email)
            // Обновление электронной почты
            if (email.isNotEmpty() && email != user.email) {
                user.verifyBeforeUpdateEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("MyLog", "Verification email sent to $email. Please verify to update your email.")
                            Toast.makeText(requireContext(), "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("MyLog", "Error sending verification email: ${task.exception?.message}")
                        }
                    }
            }

            // Обновление пароля
            if (password.isNotEmpty()) {
                user.updatePassword(password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("MyLog", "User  password updated.")
                        } else {
                            Log.e("MyLog", "Error updating password: ${task.exception?.message}")
                        }
                    }
            }


        } else {
            Log.e("MyLog", "No user is signed in.")
            Toast.makeText(requireContext(), "No user is signed in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteUser(auth: FirebaseAuth, email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
            if(it.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Вы успешно удалили аккаунт!", Toast.LENGTH_SHORT).show()
                        Log.d("MyLog", "Account was deleted.")
                        val intent = Intent(requireContext(), LoginUserActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Log.e("MyLog", "Error deleting account: ${task.exception?.message}")
                    }
                }
            } else {
                Log.e("MyLog", "Error reauthenticate!")
            }
        }
    }

    private fun signOut(auth: FirebaseAuth) {
        auth.signOut()
        Toast.makeText(requireContext(), "Вы вышли из аккаунта!", Toast.LENGTH_SHORT).show()
        Log.d("MyLog", "User sign out")
        val intent = Intent(requireContext(), LoginUserActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}