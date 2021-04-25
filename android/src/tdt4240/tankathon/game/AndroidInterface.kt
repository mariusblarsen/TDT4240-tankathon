package tdt4240.tankathon.game

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import ktx.log.info
import ktx.log.logger

private val LOG = logger<AndroidInterface>()

class AndroidInterface: FirebaseInterface {

    private val db = FirebaseFirestore.getInstance()
    private val ref = db.collection("scores")
    private var scoreBoard = hashMapOf<String, Int>()

    init {
        pullFromFirebase()
    }

    override fun sendScore(name: String, score: Int) {
        val newScore = hashMapOf(
                "name" to name,
                "score" to score
        )
        ref.whereEqualTo("name", name).get()
                .addOnSuccessListener { documents ->
                    if(!documents.isEmpty) {
                        for (document in documents) {
                            if (document.data["score"].toString().toInt() < score) {
                                ref.document(document.id).set(newScore)
                            }
                        }
                    }
                    else {
                        ref.add(newScore).addOnFailureListener {
                            LOG.info {"Could not send score" }
                        }
                    }
        }
    }

    override fun getTop10(): HashMap<String, Int> {
        pullFromFirebase()
        return scoreBoard
    }

    private fun pullFromFirebase(){
        var name = " "
        var score = -1
        ref.orderBy("score", Query.Direction.DESCENDING).limit(100).get()
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
                            LOG.info { "invalid entry" }
                        }
                    }
                }
                .addOnFailureListener{
                    LOG.info { "Could not read scoreboard" }
                }
    }
}