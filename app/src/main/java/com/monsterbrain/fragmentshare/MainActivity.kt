package com.monsterbrain.fragmentshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.monsterbrain.fragmentshare.data.DataGenerator
import com.monsterbrain.fragmentshare.data.EmailData
import com.monsterbrain.fragmentshare.fragments.EmailDetailFragment
import com.monsterbrain.fragmentshare.fragments.EmailListFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity(), EmailListFragment.ListFragmentListener, EmailDetailFragment.DetailFragmentListener {
    private lateinit var emailListFragment: EmailListFragment
    private lateinit var emailDetailFragment: EmailDetailFragment
    private lateinit var emailList: ArrayList<EmailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            emailList = DataGenerator.getEmailList()
        } else {
            emailList = savedInstanceState.getParcelableArrayList<EmailData>(SAVE_STATE_LIST) as ArrayList<EmailData>
        }

        initEmailDetailFragment()
        initEmailListFragment(emailList)
    }

    private fun initEmailDetailFragment() {
        emailDetailFragment = EmailDetailFragment.newInstance("abc", "dummy")
        supportFragmentManager.beginTransaction()
            .replace(R.id.emailDetailContainer, emailDetailFragment)
            .commit()
    }

    private fun initEmailListFragment(emailList: ArrayList<EmailData>) {
        emailListFragment = EmailListFragment.newInstance(emailList)
        supportFragmentManager.beginTransaction()
            .replace(R.id.emailListContainer, emailListFragment)
            .commit()
    }

    override fun onEmailListItemClicked(email: EmailData) {
        Log.i("xxy", "email in activity ") // todo temporary log
        emailDetailFragment.setEmailContent(email)
    }

    override fun onEmailMarkAsUnreadClicked(email: EmailData) {
        emailListFragment.updateItem(email)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(SAVE_STATE_LIST, emailList)
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val SAVE_STATE_LIST = "emailList"
    }
}