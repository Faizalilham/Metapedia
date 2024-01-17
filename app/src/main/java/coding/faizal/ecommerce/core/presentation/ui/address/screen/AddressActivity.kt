package coding.faizal.ecommerce.core.presentation.ui.address.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.databinding.ActivityAddressBinding
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.presentation.viewmodel.authentication.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.ui.addaddress.screen.AddAddressActivity
import coding.faizal.ecommerce.presentation.ui.address.adapter.AddressAdapter
import coding.faizal.ecommerce.presentation.viewmodel.user.UserViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToAddAddress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressActivity : AppCompatActivity() {
    private var _binding : ActivityAddressBinding? = null
    private val binding get() = _binding!!

    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
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
                userViewModel.getCurrentUser("Bearer $it")
            }
        }
        lifecycleScope.launch {
            userViewModel.profileResult.collect{ resource ->
                when (resource) {
                    is coding.faizal.ecommerce.core.data.Resource.Loading -> {

                    }
                    is coding.faizal.ecommerce.core.data.Resource.Success -> {
                        if (resource.data != null && resource.data.addresses.isNotEmpty()) {
                            setupRecycler(resource.data.addresses)
                            binding.listAddressEmpty.visibility = View.GONE
                        }else{
                            binding.listAddressEmpty.visibility = View.VISIBLE
                        }

                    }
                    is coding.faizal.ecommerce.core.data.Resource.Error -> {
                        val errorMessage = resource.message
                        Toast.makeText(this@AddressActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    private fun setupRecycler(data : List<coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress>){

        val labelAddressList: List<coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress> = data.mapIndexed { index, userAddress ->
            coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress(
                id = index + 1,
                name = userAddress.street,
                isSelected = false
            )
        }

        addressAdapter = AddressAdapter(this,labelAddressList,object : AddressAdapter.AddressOnClick{
            override fun labelAddress(labelAddress: coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress, position: Int) {
                addressAdapter.setSelected(position)
            }

            override fun moveToAdd(labelAddress: coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress) {
                startActivity(Intent(this@AddressActivity, AddAddressActivity::class.java).also{
                    it.putExtra(ADDRESS_DATA,labelAddress)
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