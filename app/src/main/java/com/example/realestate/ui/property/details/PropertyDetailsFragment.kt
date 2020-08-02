package com.example.realestate.ui.property.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.realestate.R
import com.example.realestate.binding.BindingAdapters
import com.example.realestate.databinding.FragmentPropertyDetailsBinding
import com.example.realestate.di.Injectable
import com.example.realestate.model.base.Status
import com.example.realestate.util.autoCleared
import javax.inject.Inject

/**
 * Fragment used to show property details
 */
class PropertyDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding by autoCleared<FragmentPropertyDetailsBinding>()

    private val viewModel: PropertyDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_property_details,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.propertyDetails.observe(viewLifecycleOwner, Observer { result ->
            binding.propertyDetailsNotAvailable = result.status == Status.ERROR
            binding.propertyDetails = result.data
            result.data?.let { propertyDetails ->
                binding.propertyCarouselView.setImageListener { position, imageView ->
                    BindingAdapters.bindImage(
                        imageView,
                        propertyDetails.realEstatePictures[position].url
                    )
                }
                binding.propertyCarouselView.pageCount = propertyDetails.realEstatePictures.size
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val params = PropertyDetailsFragmentArgs.fromBundle(requireArguments())
        viewModel.setAdvertisementId(params.advertisementId)
    }

}