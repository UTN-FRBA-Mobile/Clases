package ar.edu.utn.frba.mobile.clases.ui.main

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ar.edu.utn.frba.mobile.clases.utils.storage.fileSystem.InternalStorage
import ar.edu.utn.frba.mobile.clases.utils.storage.preferences.Preferences

@JvmInline
value class StoredImage(val uri: Uri)

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val preferences = Preferences(getApplication())

    private val mutableImages: MutableLiveData<List<StoredImage>> = MutableLiveData()
    val images: LiveData<List<StoredImage>> get() = mutableImages

    private val mutableShowAsGrid: MutableLiveData<Boolean> = MutableLiveData(preferences.showAsGrid)
    val showAsGrid: LiveData<Boolean> get() = mutableShowAsGrid
    val numberOfColumns: LiveData<Int> get() = showAsGrid.map { if (it) 3 else 1 }

    fun toggleShowAsGrid() {
        val newValue = !preferences.showAsGrid
        preferences.showAsGrid = newValue
        mutableShowAsGrid.postValue(newValue)
    }

    fun reloadImages() {
        val files = InternalStorage.getFiles(getApplication())
        val images = files?.map { file -> StoredImage(file.toUri()) } ?: listOf()
        mutableImages.postValue(images)
    }
}
