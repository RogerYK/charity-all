package com.github.rogeryk.charity_android.data

import java.math.BigDecimal
import java.util.*

data class Response<T>(var errCode: Int, var msg: String?, var data: T?)

data class Page<T>(var total: Long, var content: List<T>)

data class Banner(var id: Long, var img: String, var projectId: Long)

data class ProjectBasic(
        var id: Long,
        var name: String,
        var img: String,
        var raisedMoney: BigDecimal,
        var donorCount: Int,
        var author: UserBasic
)

data class ProjectDetail(var id: Long,
                         var name: String,
                         var img: String,
                         var content: String,
                         var summary: String,
                         var raisedMoney: BigDecimal,
                         var targetMoney: BigDecimal,
                         var startTime: Date?,
                         var endTime: Date,
                         var bumoAddress: String,
                         var donorCount: Int,
                         var author: UserBasic
)

data class Comment(
        var id: Long,
        var content: String,
        var commenter: UserBasic,
        var replyComment: Comment?,
        var subComments: List<Comment>,
        var createdTime: Date
)


data class NewsBasic(
        var id: Long,
        var img: String,
        var name: String,
        var introduce: String,
        var createdTime: Date,
        var author: UserBasic
)

data class NewsDetail(
        var id: Long,
        var img: String,
        var name: String,
        var introduce: String,
        var createdTime: Date,
        var content: String,
        var author: UserBasic
)

data class UserBasic(
        var id: Long,
        var nickName: String,
        var avatar: String
)

data class Transaction(
        var id: Long,
        var hash: String,
        var type: String,
        var project: ProjectBasic?,
        var money: BigDecimal,
        var createdTime: Date,
        var payer: UserBasic?,
        var payee: UserBasic
)
