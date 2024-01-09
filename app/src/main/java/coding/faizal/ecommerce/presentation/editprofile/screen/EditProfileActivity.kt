package coding.faizal.ecommerce.presentation.editprofile.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityEditProfileBinding
import coding.faizal.ecommerce.databinding.BottomSheetChooseImageBinding
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.menu.screen.MenuActivity
import coding.faizal.ecommerce.presentation.profile.screen.ProfileActivity
import coding.faizal.ecommerce.presentation.profile.viewmodel.ProfileViewModel
import coding.faizal.ecommerce.utils.ImagePostFunction.createFile
import coding.faizal.ecommerce.utils.ImagePostFunction.uriToFile
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToBase
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private var _binding : ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var uri : Uri
    private var getFile: File? = null

    private var i : ProfileUser? = null

    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheet()
        currentUser()
        getIntentDataUser()
        getProfileUser()

    }

    private fun getProfileUser() {
        authPreferencesViewModel.getToken().observe(this) {
            if (it != null) {
                profileViewModel.getCurrentUser("Bearer $it")
                Log.d("TOKEN",it)

            }
        }
    }

    private fun getIntentDataUser(){
        i = intent.getParcelableExtra<ProfileUser>(ProfileActivity.PROFILE_DATA)
        i?.let {
            setupViewProfile(it)
            navigation(it)
        }

    }

    private fun currentUser(){
        lifecycleScope.launch {
            profileViewModel.profileResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateShimmerVisibility(true)
                        binding.tvName.visibility = View.GONE
                        binding.tvEmail.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            updateShimmerVisibility(false)
                            binding.tvName.visibility = View.VISIBLE
                            binding.tvEmail.visibility = View.VISIBLE
                            navigation(resource.data)
                            setupViewProfile(resource.data)
                        }

                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                        Toast.makeText(this@EditProfileActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                        updateShimmerVisibility(false)
                    }
                }
            }
        }
    }

    private fun updateShimmerVisibility(isLoading: Boolean) {
        binding.shimmerViewContainerName.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.shimmerViewContainer.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            binding.shimmerViewContainerName.startShimmer()
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainerName.stopShimmer()
            binding.shimmerViewContainer.stopShimmer()
        }
    }

    private fun setupViewProfile(user : ProfileUser){
        binding.apply {
            tvName.text = user.name
            tvEmail.text = user.email
        }
    }

    private fun navigation(user : ProfileUser){
        binding.apply {
            imgBack.setOnClickListener { finish() }
            linearName.setOnClickListener { navigateToBase(this@EditProfileActivity,
                EDIT_PROFILE_DATA,user.name) }

            linearEmail.setOnClickListener { navigateToBase(this@EditProfileActivity,
                EDIT_PROFILE_DATA,user.email) }

        }
    }

    override fun onResume() {
        super.onResume()
        currentUser()
    }

    private val openGallery = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
            val myFile = uriToFile(it, this)
            getFile = myFile
            binding.imgProfile.setImageURI(it)
        }
    }
    private fun openGallery(){
        intent.type = "image/*"
        openGallery.launch("image/*")
    }

    private val openCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){
        if(it){
            binding.imgProfile.setImageURI(uri)
        }
    }

    private fun openCamera(){
        uri = FileProvider.getUriForFile(this,"${this.packageName}.provider",createFile(this))
        getFile = uriToFile(uri,this)
        openCamera.launch(uri)
    }

    private fun bottomSheet(){
        binding.tvChooseImage.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            val view = BottomSheetChooseImageBinding.inflate(layoutInflater)
            bottomSheet.apply {
                view.apply {
                    setContentView(root)
                    show()
                    cardCamera.setOnClickListener {
                        if(checkPermission()){ openCamera() }
                    }
                    cardGallery.setOnClickListener {
                        if(checkPermission()){ openGallery() }
                    }
                }
            }
        }
    }


    private fun requestLocationPermission() {
        requestPermissions(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE), 201)
    }

    private fun checkPermission():Boolean{
        val permissionCheck = checkSelfPermission(Manifest.permission.CAMERA)
        val permissionCheck2 = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionCheck3 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return if (permissionCheck == PackageManager.PERMISSION_GRANTED &&
            permissionCheck2 == PackageManager.PERMISSION_GRANTED &&
            permissionCheck3 == PackageManager.PERMISSION_GRANTED    ) {
            true
        } else {

            requestLocationPermission()
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == Manifest.permission.CAMERA && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    permissions[1] == Manifest.permission.WRITE_EXTERNAL_STORAGE  &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                    permissions[2] == Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    Toast.makeText(this, "Permissions Permitted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Permissions Denied", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else -> {
                Toast.makeText(this, "The request code doesn't match", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        const val EDIT_PROFILE_DATA = "edit_profile_data"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}