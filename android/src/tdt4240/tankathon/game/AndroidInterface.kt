package tdt4240.tankathon.game

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AndroidInterface: FirebaseInterface {
    private val db = FirebaseFirestore.getInstance()
    override fun test() {
        val data = hashMapOf(
                "first" to 1,
                "second" to 2
        )
        db.collection("dummy").document("bLGnlP1KCiwC2ucbU1HJ").set(data)

        db.collection("dummy").document("bLGnlP1KCiwC2ucbU1HJ").get()
                .addOnSuccessListener { result ->
                    val resultData = result.data
                    if (resultData != null) {
                        for (key in resultData.keys) {
                            println(resultData[key].toString())
                        }
                    }
                    println(resultData)
                }
                .addOnFailureListener { exception ->
                    println("Error getting documents.")
                }
    }
}