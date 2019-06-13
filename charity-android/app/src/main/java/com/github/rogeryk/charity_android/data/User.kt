package com.github.rogeryk.charity_android.data

import java.math.BigDecimal

data class UserInfo(
        var id: Long,
        var nickname: String,
        var avatar: String,
        var presentation: String,
        var profession: String? = null,
        var address: String? = null,
        var releasedProjectCount: Int,
        var favorUserCount: Int,
        var donatedCount: Int,
        var donatedMoney: BigDecimal
)
