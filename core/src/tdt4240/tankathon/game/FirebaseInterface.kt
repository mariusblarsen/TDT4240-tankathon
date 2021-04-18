package tdt4240.tankathon.game

interface FirebaseInterface {
    fun sendScore(name: String, score: Int)

    fun getTop10(): HashMap<String, Int>
}