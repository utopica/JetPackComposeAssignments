package com.example.graduationproject.uix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        val user = auth.currentUser
        if (user == null) {
            _authState.value = AuthState.Unauthenticated
            _currentUser.value = null
        } else {
            _authState.value = AuthState.Authenticated
            _currentUser.value = user
        }
    }

    fun login(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return //don't log in

        }
        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    _currentUser.value = auth.currentUser
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?: "Something went wrong while logging in")
                }
            }
    }

    fun signup(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return //don't log in

        }
        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    _currentUser.value = auth.currentUser
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?: "Something went wrong while signing up")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
        _currentUser.value = null
    }

}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message:String) : AuthState()
}