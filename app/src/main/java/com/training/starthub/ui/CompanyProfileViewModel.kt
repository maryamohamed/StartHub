import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.training.starthub.databinding.FragmentCompanyProfileBinding
import com.training.starthub.ui.CompanyProfileRepo
import kotlinx.coroutines.launch
import java.io.File

//class CompanyProfileViewModel(val context: Context, val binding: FragmentCompanyProfileBinding) {

//    private var _companyProfile: String? = null
//
//    fun setCompanyProfile(profile: String) {
//        _companyProfile = profile
//    }
//
//    fun getCompanyProfile(): String? {
//        return _companyProfile
//    }
//
//    fun getData(imageId: String = getCompanyProfile().toString()) {
//        val storageReference = FirebaseStorage.getInstance().getReference("profile_images/${imageId}.jpg")
//        val file = File.createTempFile("tempImage", ".jpg")
//
//        storageReference.getFile(file).addOnSuccessListener {
//            Glide.with(context).load(file).into(binding.profileImage)
//            Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show()
//        }
//    }
    /*  private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }*/


// CompanyProfileViewModel

/*
class CompanyProfileViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var auth: FirebaseAuth

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: LiveData<Map<String, String>> get() = _userData

//    private val _personalData = MutableLiveData<Map<String, String>>()
//    val personalData: LiveData<Map<String, String>> get() = _personalData

    private lateinit var repository: CompanyProfileRepo

    fun initialize(context: Context, auth: FirebaseAuth) {
        this.context = context
        this.auth = auth
        repository = CompanyProfileRepo(context, auth)
    }

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }

    fun uploadImage(fileUri: Uri) {
        viewModelScope.launch {
            val uri = repository.uploadImage(fileUri)
            uri?.let {
                setImageUrl(it.toString())
                saveImageIdToFirestore(it.toString())
            }
        }
    }

    fun downloadImage(imageId: String) {
        viewModelScope.launch {
            val file = repository.downloadImage(imageId)
            if (file != null) {
                // Use Glide or other means to load the image
            } else {
                // Handle case where file does not exist
//                setImageUrl(null.toString())
            }
        }
    }

    fun fetchUserDescriptionAndCategory(userId: String) {
        Toast.makeText(context, "fetchUserDescriptionAndImage: $userId", Toast.LENGTH_SHORT).show()
        viewModelScope.launch {
            val data = repository.fetchUserDescriptionAndCategory()
            data?.let { _userData.value = it }
        }
    }
//    fun fetchPersonalData(userId: String) {
//        Toast.makeText(context, "fetchPersonalData: $userId", Toast.LENGTH_SHORT).show()
//        viewModelScope.launch {
//            val data = repository.fetchPersonalData()
//            data?.let { _personalData.value = it }
//        }
//    }

    private fun saveImageIdToFirestore(imageId: String) {
        Toast.makeText(context, "saveImageIdToFirestore: $imageId", Toast.LENGTH_SHORT).show()
        viewModelScope.launch {
            repository.saveUpdatedToFirestore(imageId, userData.value!!)
        }
    }
}*/

class CompanyProfileViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var auth: FirebaseAuth

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    private val _userData = MutableLiveData<Map<String, String>>()
    val userData: LiveData<Map<String, String>> get() = _userData

    private lateinit var repository: CompanyProfileRepo

    fun initialize(context: Context, auth: FirebaseAuth) {
        this.context = context
        this.auth = auth
        repository = CompanyProfileRepo(context, auth)
    }

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }

    fun uploadImage(fileUri: Uri) {
        viewModelScope.launch {
            val uri = repository.uploadImage(fileUri)
            uri?.let {
                setImageUrl(it.toString())
                saveImageIdToFirestore(it.toString())
            }
        }
    }

    fun downloadImage(imageId: String) {
        viewModelScope.launch {
            val file = repository.downloadImage(imageId)
            file?.let {
//                Glide.with(context).load(file).into(binding.profileImage)
                // Use Glide or other means to load the image
            }
        }
    }

    fun fetchUserDescriptionAndCategory(userId: String) {
        viewModelScope.launch {
            val data = repository.fetchUserDescriptionAndCategory(userId)
            data?.let { _userData.value = it }
        }
    }

//    fun updateUserData(description: String, category: String) {
//        viewModelScope.launch {
//            repository.updateUserData(description, category)
//        }
//    }

    private fun saveImageIdToFirestore(imageId: String) {
        viewModelScope.launch {
            repository.saveImageIdToFirestore(userData.value?: emptyMap())
        }
    }
}
