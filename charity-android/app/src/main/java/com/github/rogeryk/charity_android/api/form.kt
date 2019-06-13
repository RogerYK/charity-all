package com.github.rogeryk.charity_android.api

import java.math.BigDecimal


data class LoginForm(
        var phoneNumber: String,
        var password: String
)

data class DonationForm(
       val projectId: Long,
       val amount: BigDecimal
)

data class CommentForm(
        var projectId: Long?,
        var parentId: Long?,
        var replyId: Long?,
        var content: String
)
