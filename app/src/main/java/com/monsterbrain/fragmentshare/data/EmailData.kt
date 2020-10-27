package com.monsterbrain.fragmentshare.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class EmailData(var fromAddress: String?, var subject: String?, var content: String?, var date: String?): Parcelable {
    var hasRead = false
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fromAddress)
        parcel.writeString(subject)
        parcel.writeString(content)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmailData> {
        override fun createFromParcel(parcel: Parcel): EmailData {
            return EmailData(parcel)
        }

        override fun newArray(size: Int): Array<EmailData?> {
            return arrayOfNulls(size)
        }
    }
}
