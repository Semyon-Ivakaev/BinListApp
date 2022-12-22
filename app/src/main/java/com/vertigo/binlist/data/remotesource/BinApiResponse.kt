package com.vertigo.binlist.data.remotesource

import com.google.gson.annotations.SerializedName

data class BinApiResponse(

	@SerializedName("number")
	val number: Number?,
	@SerializedName("country")
	val country: Country?,
	@SerializedName("bank")
	val bank: Bank?,
	@SerializedName("scheme")
	val scheme: String?,
	@SerializedName("prepaid")
	val prepaid: Boolean?,
	@SerializedName("type")
	val type: String?,
	@SerializedName("brand")
	val brand: String?
)

data class Country(

	@SerializedName("emoji")
	val emoji: String?,
	@SerializedName("latitude")
	val latitude: Int?,
	@SerializedName("alpha2")
	val alpha2: String?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("numeric")
	val numeric: String?,
	@SerializedName("currency")
	val currency: String?,
	@SerializedName("longitude")
	val longitude: Int?
)

data class Bank(

	@SerializedName("phone")
	val phone: String?,
	@SerializedName("city")
	val city: String?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("url")
	val url: String?
)

data class Number(

	@SerializedName("length")
	val length: Int?,
	@SerializedName("luhn")
	val luhn: Boolean?
)
