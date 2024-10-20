import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginCustomerRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun signInUser(email: String, password: String, userType: String): Result<Boolean> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            user?.let {
                val document = db.collection("Customers").document(user.uid).get().await()
                val retrievedUserType = document.getString("userType")
                val isEmailVerified = user.isEmailVerified

                if (retrievedUserType == userType && isEmailVerified) {
                    Result.success(true)
                } else {
                    val errorMessage = when {
                        retrievedUserType != userType -> "You are not authorized to log in as a $userType."
                        !isEmailVerified -> "Please verify your email before logging in."
                        else -> "Login failed."
                    }
                    Result.failure(Exception(errorMessage))
                }
            } ?: Result.failure(Exception("User not found."))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
