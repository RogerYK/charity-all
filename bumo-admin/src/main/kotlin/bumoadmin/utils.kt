package bumoadmin

import io.bumo.model.response.BaseResponse

fun <T:BaseResponse> T.orThrow():T {
    if (errorCode != 0) {
        throw BumoException(errorCode, errorDesc)
    }
    return this;
}