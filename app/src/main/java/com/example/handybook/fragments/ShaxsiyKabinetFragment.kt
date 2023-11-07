package com.example.handybook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handybook.MyShared
import com.example.handybook.R
import com.example.handybook.adapters.BookCabinetAdapter
import com.example.handybook.adapters.BooksAdapter
import com.example.handybook.adapters.Kitob2Adapter
import com.example.handybook.databinding.FragmentShaxsiyKabinetBinding
import com.example.handybook.models.Book
import com.example.handybook.networking.APIClient
import com.example.handybook.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShaxsiyKabinetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShaxsiyKabinetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var books = mutableListOf<Book>()
    var allBooks = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentShaxsiyKabinetBinding.inflate(inflater,container,false)
        val api = APIClient.getInstance().create(APIService::class.java)
        val shared = MyShared.getInstance(requireContext())
        var user = shared.getUser()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment())
                .commit()

        }
        api.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (response.isSuccessful && response.body() != null){
                    allBooks = response.body()!!.toMutableList()
                    for (book in allBooks) {
                        if (book.status == 1){
                            books.add(book)
                        }
                    }
                    var adapter = BookCabinetAdapter(books)
                    binding.recyclerView.adapter = adapter

                    var adapter1 = Kitob2Adapter(allBooks)
                    binding.recyclerView2.adapter = adapter1

                    binding.readingrn.text = books.size.toString()
                    binding.readFinished.text = allBooks.size.toString()
                    binding.savedbooks.text = "0"
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })

        binding.username.text = user!!.username
        binding.username.text = user.id.toString()
        binding.imageView2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, SignOutFragment())
                .commit()
        }
        binding.textView8.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, OqilganKitoblarFragment())
                .commit()
        }
        binding.textView5.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, OqilayotganKitoblarFragment())
                .commit()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShaxsiyKabinetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShaxsiyKabinetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}