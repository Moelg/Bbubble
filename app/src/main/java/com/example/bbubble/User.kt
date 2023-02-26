package com.example.bbubble

class User {
    var userName: String? = null
    var email: String? = null
    var uid: String? = null

    constructor(){}

    constructor(userName: String?, email: String?, uid: String?){
        this.userName = userName
        this.email = email
        this.uid = uid
    }
}