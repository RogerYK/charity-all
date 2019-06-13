package bumoadmin

class BumoException(errorCode:Int, errorDesc:String) :Exception() {
    val errorCode:Int = errorCode

    val errorDesc:String = errorDesc
}