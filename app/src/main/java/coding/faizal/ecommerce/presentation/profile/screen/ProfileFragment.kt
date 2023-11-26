package coding.faizal.ecommerce.presentation.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}