package coding.faizal.ecommerce.presentation.addaddress.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityAddAddressBinding
import coding.faizal.ecommerce.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.addaddress.adapter.AddAddressAdapter
import coding.faizal.ecommerce.presentation.address.screen.AddressActivity
import coding.faizal.ecommerce.presentation.address.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAddressActivity : AppCompatActivity() {
    private var _binding : ActivityAddAddressBinding? = null
    private val binding get() = _binding!!
    private lateinit var addressAdapter : AddAddressAdapter

    private val addressViewModel by viewModels<AddressViewModel>()
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAddressRecycler()
        addressViewModel.getAllLabelAddressData()
        addAddress()
        val i = intent.getParcelableExtra<LabelAddress>(AddressActivity.ADDRESS_DATA)
        if(i != null){
            getDataIntent(i)
        }

        binding.apply {

            etAddress.setOnClickListener {
                expandableLayout.expand()
            }

            etName.addTextChangedListener(watcher)
            etPhoneNumber.addTextChangedListener(watcher)
            etAddress.addTextChangedListener(watcher)
            etCompleteAddress.addTextChangedListener(watcher)
            etCity.addTextChangedListener(watcher)
            etNotes.addTextChangedListener(watcher)
        }
    }

    private fun getDataIntent(i : LabelAddress){
        binding.apply {
            val split = i.name.split(",")
            etName.setText(split[0])
        }
    }
    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable?) {

          binding.apply {
              val allFieldsFilled = etName.text.toString().isNotEmpty() &&
                      etPhoneNumber.text.toString().isNotEmpty() &&
                      etAddress.text.toString().isNotEmpty() &&
                      etCompleteAddress.text.toString().isNotEmpty() &&
                      etCity.text.toString().isNotEmpty() &&
                      etNotes.text.toString().isNotEmpty()


              btnSave.isEnabled = allFieldsFilled
          }
        }
    }

    private fun setAddressRecycler(){
       lifecycleScope.launch {
           addressViewModel.productSizeData.collect{ data ->
               addressAdapter = AddAddressAdapter(this@AddAddressActivity,data ,
                   object : AddAddressAdapter.AddressOnClick{
                       override fun labelAddress(labelAddress: LabelAddress, position: Int) {
                           addressAdapter.setSelected(position)
                           binding.etAddress.setText(labelAddress.name)
                           binding.expandableLayout.collapse()

                       }
                   })
               binding.rvAddress.apply {
                   adapter = addressAdapter
                   layoutManager = LinearLayoutManager(this@AddAddressActivity,
                       LinearLayoutManager.HORIZONTAL,false)
               }
           }
       }
    }

    private fun addAddress(){
        binding.apply{
            btnSave.setOnClickListener {
                val name = etName.text.toString()
                val address = etAddress.text.toString()
                val city = etCity.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()
                val fullAddress = etCompleteAddress.text.toString()
                val notes = etNotes.text.toString()
                val country = etCountry.text.toString()
                val postalCode = etPostalCode.text.toString()

                val completeAddress = "$address, $name, $phoneNumber, $fullAddress, $notes"
                authPreferencesViewModel.getToken().observe(this@AddAddressActivity){
                    if(it != null){
                        val addresses = listOf(
                            UserAddress(
                                completeAddress,
                                city,
                                country,
                                postalCode
                            )
                        )
                        addressViewModel.updateAddress("Bearer $it", addresses)
                    }
                }

                lifecycleScope.launch {
                    addressViewModel.addAddressResult.collect{ resource ->
                        when(resource){
                            is Resource.Loading -> {
                                Toast.makeText(this@AddAddressActivity, "Loading ...", Toast.LENGTH_SHORT).show()
                            }

                            is Resource.Success -> {
                                Toast.makeText(this@AddAddressActivity, "Successfully add address ...", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            is Resource.Error -> {
                                Toast.makeText(this@AddAddressActivity, "${resource.message}", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}