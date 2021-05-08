package ar.edu.utn.frba.mobile.clases.utils.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases.R

object Permissions {

    private fun hasPermissions(context: Context, permissionCode: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permissionCode) == PackageManager.PERMISSION_GRANTED
    }

    /// Retorna true si ya tiene permiso
    fun checkForPermissions(fragment: Fragment, permissionCode: String, requestCode: Int, reason: String): Boolean {
        if (hasPermissions(fragment.context!!, permissionCode)) {
            return true
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.activity!!, permissionCode)) {
            showStoragePermissionExplanation(fragment, permissionCode, reason, requestCode)
        }
        else {
            dispatchStoragePermissionRequest(fragment, permissionCode, requestCode)
        }
        return false
    }

    private fun dispatchStoragePermissionRequest(fragment: Fragment, permissionCode: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permissionCode), requestCode)
    }

    private fun showStoragePermissionExplanation(fragment: Fragment, permissionCode: String, reason: String, requestCode: Int) {
        val builder = AlertDialog.Builder(fragment.context!!)
        builder.setTitle(R.string.permissionTitle)
        builder.setCancelable(true)
        builder.setMessage(reason)
        builder.setPositiveButton(R.string.ok) { _, _ ->
            dispatchStoragePermissionRequest(fragment, permissionCode, requestCode)
        }
        builder.show()
    }
}