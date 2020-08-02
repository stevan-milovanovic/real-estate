package com.example.realestate.ui.property

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.realestate.AppExecutors
import com.example.realestate.R
import com.example.realestate.databinding.FragmentPropertiesBinding
import com.example.realestate.di.Injectable
import com.example.realestate.util.autoCleared
import javax.inject.Inject

/**
 * Fragment used to show list of properties
 */
class PropertiesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var binding by autoCleared<FragmentPropertiesBinding>()
    private var adapter by autoCleared<PropertyListAdapter>()

    private val propertiesViewModel: PropertiesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_properties,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        propertiesViewModel.properties.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)
        })

        propertiesViewModel.favourites.observe(viewLifecycleOwner, Observer { result ->
            adapter.notifyDataSetChanged()
        })

        val adapter =
            PropertyListAdapter(appExecutors, { property ->
                propertiesViewModel.toggleFavorite(property)
            })
            { property ->
                findNavController().navigate(
                    PropertiesFragmentDirections.showPropertyDetails(
                        property.advertisementId
                    )
                )
            }
        binding.propertiesRecyclerView.adapter = adapter
        this.adapter = adapter
    }

}