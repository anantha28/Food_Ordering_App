package com.internshala.example.foodapp

class Profile(
    val userid:String,
    val userName:String,
    val userPhoneNumber:String,
    val userEmail:String,
    val userAddress:String
)
{
    fun getProfileInfo()
    {
        val info= arrayListOf<String>(userid,userName,userPhoneNumber,userEmail,userAddress)
    }
}