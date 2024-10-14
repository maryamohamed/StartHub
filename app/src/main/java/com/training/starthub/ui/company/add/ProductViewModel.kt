package com.training.starthub.ui.company.add


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.starthub.ui.company.add.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    // دالة لتحميل الصورة وحفظ المنتج
    fun uploadProduct(fileUri: Uri, name: String, description: String, price: String, category: String) {
        viewModelScope.launch {
            try {
                // رفع الصورة
                val imageUrl = repository.uploadImage(fileUri)

                // حفظ بيانات المنتج
                repository.saveProductData(name, description, price, category, imageUrl)

                // تحديث رسالة النجاح
                successMessage.postValue("Product added successfully")
            } catch (e: Exception) {
                // تحديث رسالة الخطأ
                errorMessage.postValue("Failed to add product: ${e.message}")
            }
        }
    }
}
