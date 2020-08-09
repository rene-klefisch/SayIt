package com.example.sayit

data class DataAntwort(val themenId : String, val content : String){
    constructor() : this("", "")
}