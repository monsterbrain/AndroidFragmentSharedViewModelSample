package com.monsterbrain.fragmentshare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monsterbrain.fragmentshare.R
import com.monsterbrain.fragmentshare.data.EmailData
import kotlinx.android.synthetic.main.list_item_email.view.*

class EmailListAdapter(private val items: ArrayList<EmailData>, private val selectListener: (EmailData) -> Unit): RecyclerView.Adapter<EmailListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.list_item_email))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], selectListener)

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(emailData: EmailData, selectListener: (EmailData) -> Unit) {
            with(itemView) {
                subjectTextView.text = emailData.subject
                fromAddressTextView.text = emailData.fromAddress
                emailDate.text = emailData.date
                setOnClickListener {
                    selectListener(emailData)
                }
            }
        }
    }

    // Extension function
    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }
}