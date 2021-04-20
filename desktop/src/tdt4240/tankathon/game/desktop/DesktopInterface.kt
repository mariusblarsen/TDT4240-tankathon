package tdt4240.tankathon.game.desktop

import tdt4240.tankathon.game.FirebaseInterface

class DesktopInterface: FirebaseInterface {
    override fun sendScore(name: String, score: Int) {
        TODO("Not yet implemented")
    }

    override fun getTop10(): HashMap<String, Int> {
        return hashMapOf<String, Int>(
                "marius" to 120,
                "bjorn" to 1200,
                "johan" to 900,
                "kristian" to 700,
                "oeystein" to 1299
        )
    }
}