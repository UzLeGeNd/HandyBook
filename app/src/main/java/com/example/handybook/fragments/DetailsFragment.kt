package com.example.handybook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.handybook.R
import com.example.handybook.databinding.FragmentDetailsBinding
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("book")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        var bundle = Bundle()
        bundle.putInt("id",id)


        var onlinebook = OnlineBookFragment()
        onlinebook.arguments = bundle
        setCurrentTabFragment(0,bundle)

        binding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setCurrentTabFragment(tab!!.position, bundle)
                Log.d("AAA", tab.position.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()
        }



        return binding.root
    }

    fun setCurrentTabFragment(position: Int, bundle: Bundle) {
        when(position){
            0->{
                var ebook = OnlineBookFragment()
                ebook.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, ebook)
                    .commit()
            }
            1->{
                var audiobook = AudioBookFragment()
                audiobook.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, audiobook)
                    .commit()
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}