package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

// Extended function to convert string into a index-character map
fun String.toCharMap(): MutableMap<Int, Char> {
    val result = mutableMapOf<Int, Char>()
    for (i in 0 until this.length) {
        result[i] = get(i)
    }
    return result
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0
    var wrongPosition = 0

    //mutable hashmap to store secret
    val mutableSecretCache = secret.toCharMap()
    val mutableGuessCache = guess.toCharMap()

    //first get all right positions
    for((key, value) in mutableGuessCache) {
        if(value == mutableSecretCache.get(key)) {
            rightPosition++
            mutableSecretCache[key] = ' '
            mutableGuessCache[key] = ' '
        }
    }

    fun getWrongPositions(ch: Char?, p: Int) {

        //each time a right position or wrong pos is found remove that char from the secret cache
        for((key, value) in mutableSecretCache) {
            if(ch != ' ' && ch == value) {
                wrongPosition++
                mutableSecretCache[key] = ' '
                mutableGuessCache[p] = ' '
                return
            }
        }
    }

    // now get the wrong positions
    for ((key, value) in mutableGuessCache) {
        val ch = mutableGuessCache[key]
        if(ch != ' ')
            getWrongPositions(ch, key)
    }
    return Evaluation(rightPosition, wrongPosition)
}