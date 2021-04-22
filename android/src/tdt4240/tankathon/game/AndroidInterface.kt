package tdt4240.tankathon.game

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AndroidInterface: FirebaseInterface {

    private val db = FirebaseFirestore.getInstance()
    private var scoreBoard = hashMapOf<String, Int>()

    init {
        var name = " "
        var score = 0
        db.collection("scores").orderBy("score").limit(10).get()
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
                        if (name != " " && score != 0) {
                            scoreBoard.put(name, score)
                        }
                        else {
                            println("****************************Could not get scoreboard******************************")
                        }
                    }
                }
                .addOnFailureListener{
                    println("Could not read scoreboard")
                }
    }

    fun test() {
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

    override fun sendScore(name: String, score: Int) {
        val newScore = hashMapOf(
                "name" to name,
                "score" to score
        )
        db.collection("scores").add(newScore)
                .addOnSuccessListener {
            println("score: " + score.toString() + "added for " + name)
        }
                .addOnFailureListener{
                    println("Could not send score")
                }
    }

    override fun getTop10(): HashMap<String, Int> {
        var name = " "
        var score = 0
        db.collection("scores").orderBy("score").limit(10).get()
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
                        if (name != " " && score != 0) {
                            scoreBoard.put(name, score)
                        }
                        else {
                            println("Could not get scoreboard")
                        }
                    }
                }
                .addOnFailureListener{
                    println("Could not read scoreboard")
                }
        return scoreBoard
    }
}