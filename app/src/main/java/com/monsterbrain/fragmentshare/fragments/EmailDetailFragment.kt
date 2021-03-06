package com.monsterbrain.fragmentshare.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.monsterbrain.fragmentshare.MainActivity
import com.monsterbrain.fragmentshare.R
import com.monsterbrain.fragmentshare.data.EmailData
import kotlinx.android.synthetic.main.fragment_email_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmailDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmailDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listener: DetailFragmentListener? = null

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val model: MainActivity.SharedViewModel by activityViewModels()

    interface DetailFragmentListener {
        fun onEmailMarkAsUnreadClicked(email: EmailData)
    }

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_detail, container, false)
    }

    fun setEmailContent(emailData: EmailData){
        contentTextview.text = emailData.content
        buttonMarkAs.text = "Mark as Unread"
        buttonMarkAs.setOnClickListener {
            emailData.hasRead = false
            //listener?.onEmailMarkAsUnreadClicked(emailData)
            model.markAsUnread(emailData)
        }
    }

    override fun onAttach(context: Context) {
        if (activity is DetailFragmentListener) {
            listener = activity as DetailFragmentListener
        }
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.selected.observe(viewLifecycleOwner, Observer<EmailData> { item ->
            // Update the UI
            Log.i(TAG, "onViewCreated:  in model observer")
            setEmailContent(item)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmailDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmailDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        const val TAG = "EmailDetailFragment"
    }
}