class MessagePool {
    companion object {
        const val MAX_MESSAGE_LENGTH = 50
        private var pool: Message? = null
        private var poolSize = 0
    }

    fun getMessage(): Message {
        if (poolSize > 0) {
            poolSize--
            val temp = pool
            pool = pool!!.next
            temp!!.reset()
            return temp
        }
        return Message()
    }

    fun recycle(message: Message) {
        if (poolSize < MAX_MESSAGE_LENGTH) {
            message.next = pool
            pool = message
            poolSize++
        }
    }
}

data class Message(
    var data: String? = null
) {
    var next: Message? = null

    fun reset() {
        data = null
        next = null
    }
}
