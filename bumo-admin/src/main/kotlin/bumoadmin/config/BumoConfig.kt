package bumoadmin.config

import io.bumo.SDK

object config {
    val address = "buQYzuZW8XoeAGqsh7TKqAXYUWfswhPDocjA"

    val privateKey = "privbwwBPvX4zwDh1nZeiEcKNRLisN5ht4hVTwwuB6BMd3n17bk84qDz"

    val sdk: SDK = SDK.getInstance("http://seed1.bumotest.io:26002")
}