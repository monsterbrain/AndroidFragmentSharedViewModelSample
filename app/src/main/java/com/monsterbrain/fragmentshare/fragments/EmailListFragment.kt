package com.monsterbrain.fragmentshare.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.monsterbrain.fragmentshare.R
import com.monsterbrain.fragmentshare.adapter.EmailListAdapter
import com.monsterbrain.fragmentshare.data.EmailData
import kotlinx.android.synthetic.main.fragment_email_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM_EMAIL_LIST = "param_email_list"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmailListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmailListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var emailList: ArrayList<EmailData>? = null
    private var param2: String? = null

    private var listener: ListFragmentListener? = null

    interface ListFragmentListener {
        fun onEmailListItemClicked(email: EmailData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emailList = it.getParcelableArrayList<EmailData>(ARG_PARAM_EMAIL_LIST)
            param2 = it.getString(ARG_PARAM2)
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
    }

    private fun setupList(emailList: ArrayList<EmailData>) {
        emailRecyclerView.adapter = EmailListAdapter(emailList) {
            listener?.onEmailListItemClicked(it)
            Log.i("xxy", "email data click ") // todo temporary log
        }
        emailRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onAttach(context: Context) {
        if (activity is ListFragmentListener) {
            listener = activity as ListFragmentListener
        }
        super.onAttach(context)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param emailList List of emails.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmailListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(emailList: ArrayList<EmailData>, param2: String) =
            EmailListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM_EMAIL_LIST, emailList)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}