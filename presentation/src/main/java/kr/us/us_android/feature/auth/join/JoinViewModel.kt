package kr.us.us_android.feature.auth.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.us.us_android.data.auth.request.JoinRequest

class JoinViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _id = MutableLiveData<String>()
    val id: LiveData<String> get() = _id

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _birthdate = MutableLiveData<String>()
    val birthdate: LiveData<String> get() = _birthdate

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setId(value: String) {
        _id.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setBirthdate(value: String) {
        _birthdate.value = value
    }

    fun setUsername(value: String) {
        _username.value = value
    }

    fun getRegisterRequestData(): LiveData<JoinRequest> {
        val userData = MutableLiveData<JoinRequest>()
        val email = _email.value ?: ""
        val password = _password.value ?: ""
        val birthDate = _birthdate.value ?: ""
        val username = _username.value ?: ""
        val id = _id.value ?: ""

        userData.value = JoinRequest(username, birthDate, id, email, password)
        return userData
    }
}