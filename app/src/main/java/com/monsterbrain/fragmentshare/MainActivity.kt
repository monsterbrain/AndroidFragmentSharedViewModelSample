package com.monsterbrain.fragmentshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.monsterbrain.fragmentshare.data.DataGenerator
import com.monsterbrain.fragmentshare.data.EmailData
import com.monsterbrain.fragmentshare.fragments.EmailDetailFragment
import com.monsterbrain.fragmentshare.fragments.EmailListFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity(), EmailListFragment.ListFragmentListener {
    private lateinit var emailDetailFragment: EmailDetailFragment
    private lateinit var emailList: ArrayList<EmailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailList = DataGenerator.getEmailList()

        initEmailDetailFragment()
        initEmailListFragment(emailList)
    }

    private fun initEmailDetailFragment() {
        emailDetailFragment = EmailDetailFragment.newInstance("abc", "dummy")
        supportFragmentManager.beginTransaction()
            .add(R.id.emailDetailContainer, emailDetailFragment)
            .commit()
    }

    private fun initEmailListFragment(emailList: ArrayList<EmailData>) {
        val emailListFragment = EmailListFragment.newInstance(emailList, "dummy")
        supportFragmentManager.beginTransaction()
            .add(R.id.emailListContainer, emailListFragment)
            .commit()
    }

    override fun onEmailListItemClicked(email: EmailData) {
        Log.i("xxy", "email in activity ") // todo temporary log
        emailDetailFragment.setEmailContent(email)
    }
}