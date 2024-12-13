package com.dicoding.gloo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(

    @field:SerializedName("message")
    val message: String?,

    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("product")
    val product: ProductsItem?
) : Parcelable

data class ProductsItem(

    @field:SerializedName("karbohidrat")
    val karbohidrat: String?,

    @field:SerializedName("glukosa")
    val glukosa: String?,

    @field:SerializedName("kolestrol")
    val kolestrol: String?,

    @field:SerializedName("Nama_produk")
    val namaProduk: String?,

    @field:SerializedName("protein")
    val protein: String?,

    @field:SerializedName("serat")
    val serat: String?,

    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("Ukuran_porsi")
    val ukuranPorsi: String?,

    @field:SerializedName("garam")
    val garam: String?,

    @field:SerializedName("lemak")
    val lemak: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(karbohidrat)
        parcel.writeString(glukosa)
        parcel.writeString(kolestrol)
        parcel.writeString(namaProduk)
        parcel.writeString(protein)
        parcel.writeString(serat)
        parcel.writeString(id)
        parcel.writeString(ukuranPorsi)
        parcel.writeString(garam)
        parcel.writeString(lemak)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductsItem> {
        override fun createFromParcel(parcel: Parcel): ProductsItem {
            return ProductsItem(parcel)
        }

        override fun newArray(size: Int): Array<ProductsItem?> {
            return arrayOfNulls(size)
        }
    }
}
