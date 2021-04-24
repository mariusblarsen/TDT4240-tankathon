package tdt4240.tankathon.game

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AndroidInterface: FirebaseInterface {

    private val db = FirebaseFirestore.getInstance()
    private var scoreBoard = hashMapOf<String, Int>()

    init {
        pullFromFirebase()
    }

    override fun sendScore(name: String, score: Int) {
        val newScore = hashMapOf(
                "name" to name,
                "score" to score
        )
        db.collection("scores").add(newScore)
                .addOnSuccessListener {
            println("score: " + score.toString() + " added for " + name)
        }
                .addOnFailureListener{
                    println("Could not send score")
                }
    }

    override fun getTop10(): HashMap<String, Int> {
        pullFromFirebase()
        return scoreBoard
    }

    private fun pullFromFirebase(){
        var name = " "
        var score = -1
        db.collection("scores").orderBy("score", Query.Direction.DESCENDING).limit(100).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val entry = document.data
                        var num = 0
                        for (key in entry.keys) {
                            if (num == 0) {
                                score = entry[key].toString().toInt()
                                num = 1
                            }
                            else if (num == 1) {
                                name = entry[key].toString()
                                num = 2
                            }
                            else {
                                break
                            }
                        }
                        if (name != " " && score != -1) {
                            scoreBoard.put(name, score)
                        }
                        else {
                            println("Invalid entry")
                        }
                    }
                }
                .addOnFailureListener{
                    println("Could not read scoreboard")
                }
    }
}