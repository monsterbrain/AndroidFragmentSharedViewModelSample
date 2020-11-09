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
import androidx.recyclerview.widget.LinearLayoutManager
import com.monsterbrain.fragmentshare.MainActivity
import com.monsterbrain.fragmentshare.R
import com.monsterbrain.fragmentshare.adapter.EmailListAdapter
import com.monsterbrain.fragmentshare.data.EmailData
import kotlinx.android.synthetic.main.fragment_email_list.*

private const val ARG_PARAM_EMAIL_LIST = "param_email_list"

/**
 * A simple [Fragment] subclass.
 * Use the [EmailListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmailListFragment : Fragment() {
    private var emailList: ArrayList<EmailData>? = null
    private var listener: ListFragmentListener? = null


    private val model: MainActivity.SharedViewModel by activityViewModels()

    interface ListFragmentListener {
        fun onEmailListItemClicked(email: EmailData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emailList = it.getParcelableArrayList<EmailData>(ARG_PARAM_EMAIL_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        emailList?.let { mailList -> setupList(mailList) }

        model.unreadClickedEmail.observe(viewLifecycleOwner, Observer<EmailData> { item ->
            // Update the UI
            Log.i(EmailDetailFragment.TAG, "onViewCreated:  in model List back")
            updateItem(item)
        })
    }

    private fun setupList(emailList: ArrayList<EmailData>) {
        emailRecyclerView.adapter = EmailListAdapter(emailList) { emailData, position ->
            model.select(emailData)
            //listener?.onEmailListItemClicked(emailData)
            emailRecyclerView.adapter?.notifyItemChanged(position)
        }
        emailRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onAttach(context: Context) {
        if (activity is ListFragmentListener) {
            listener = activity as ListFragmentListener
        }
        super.onAttach(context)
    }

    fun updateItem(email: EmailData) {
        emailList?.let {
            val position = it.indexOf(email)
            emailRecyclerView.adapter?.notifyItemChanged(position)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param emailList List of emails.
         * @return A new instance of fragment EmailListFragment.
         */
        @JvmStatic
        fun newInstance(emailList: ArrayList<EmailData>) =
            EmailListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM_EMAIL_LIST, emailList)
                }
            }
    }
}