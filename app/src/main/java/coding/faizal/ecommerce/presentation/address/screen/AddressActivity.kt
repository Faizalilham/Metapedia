package coding.faizal.ecommerce.presentation.address.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityAddressBinding
import coding.faizal.ecommerce.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.addaddress.screen.AddAddressActivity
import coding.faizal.ecommerce.presentation.address.adapter.AddressAdapter
import coding.faizal.ecommerce.presentation.profile.viewmodel.ProfileViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToAddAddress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressActivity : AppCompatActivity() {
    private var _binding : ActivityAddressBinding? = null
    private val binding get() = _binding!!

    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var addressAdapter : AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation()
        getAddress()
    }

    private fun navigation(){
        binding.apply {
            imgBack.setOnClickListener { finish() }
            tvAddAddress.setOnClickListener { navigateToAddAddress(this@AddressActivity) }
            btnAddAddress.setOnClickListener { navigateToAddAddress(this@AddressActivity) }
        }
    }

    private fun getAddress() {
        authPreferencesViewModel.getToken().observe(this) {
            if (it != null) {
                profileViewModel.getCurrentUser("Bearer $it")
            }
        }
        lifecycleScope.launch {
            profileViewModel.profileResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        if (resource.data != null && resource.data.addresses.isNotEmpty()) {
                            setupRecycler(resource.data.addresses)
                            binding.listAddressEmpty.visibility = View.GONE
                        }else{
                            binding.listAddressEmpty.visibility = View.VISIBLE
                        }

                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                        Toast.makeText(this@AddressActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    private fun setupRecycler(data : List<UserAddress>){

        val labelAddressList: List<LabelAddress> = data.mapIndexed { index, userAddress ->
            LabelAddress(
                id = index + 1,
                name = userAddress.street,
                isSelected = false
            )
        }

        addressAdapter = AddressAdapter(this,labelAddressList,object : AddressAdapter.AddressOnClick{
            override fun labelAddress(labelAddress: LabelAddress, position: Int) {
                addressAdapter.setSelected(position)
            }

            override fun moveToAdd(labelAddress: LabelAddress) {
                startActivity(Intent(this@AddressActivity,AddAddressActivity::class.java).also{
                    it.putExtra(AddressActivity.ADDRESS_DATA,labelAddress)
                })
            }

        })

        binding.rvAddress.apply {
            adapter = addressAdapter
            layoutManager = LinearLayoutManager(this@AddressActivity)
        }
    }

    companion object{
        const val ADDRESS_DATA = "address_data"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}