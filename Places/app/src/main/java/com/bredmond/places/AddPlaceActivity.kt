package com.bredmond.places

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.*


class AddPlaceActivity : AppCompatActivity(), View.OnClickListener {

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        //        This is setting up a Toolbar in this Activity
        setSupportActionBar(findViewById(R.id.toolbar_add_place))
        val toolbarAddPlace: Toolbar = findViewById(R.id.toolbar_add_place)
        //        This creates a back button to go to the HOME ACTIVITY
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarAddPlace.setNavigationOnClickListener {
            onBackPressed()
        }
        
        dateSetListener = DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        val etDate: TextView = findViewById(R.id.et_date)
        etDate.setOnClickListener(this)

        val tvAddImage: TextView = findViewById(R.id.tv_add_image)
        tvAddImage.setOnClickListener(this)
    }

//    Our App extends ViewOnClickListener (top of app), so we overide that
//    and create functionality for when the user clicks on the button
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.et_date -> {
                DatePickerDialog(this@AddPlaceActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems = arrayOf("Select photo from Gallery",
                    "Capture photo from camera")
                pictureDialog.setItems(pictureDialogItems) {
                    dialog, which ->
                        when(which) {
                            0 -> choosePhotoFromGallery()
                            1 -> Toast.makeText(
                                this@AddPlaceActivity,
                                "Camera selection coming soon...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                pictureDialog.show()
            }
        }

    }

//    Here we are using the Dexter Library to Allow Permissions Easily that we need for our app
    private fun choosePhotoFromGallery() {
        Dexter.withContext(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    Toast.makeText(this@AddPlaceActivity,
                        "Storage READ/WRITE permission are granted. Now you can select and image from GALLERY",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
    }

//    This is how we sent the user to the settings menu to access the permissions
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("It looks like you have turned off permission required" +
                "for this feature. It can be enabled under the Application Settings ")
            .setPositiveButton("GO TO SETTINGS") {
                _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }.show()
    }


    private fun updateDateInView() {
        val myFormat = "MM.dd.yyyy"
        val sdf = java.text.SimpleDateFormat(myFormat, Locale.getDefault())
        val etDate: TextView = findViewById(R.id.et_date)
        etDate.setText(sdf.format(cal.time).toString())
    }
}