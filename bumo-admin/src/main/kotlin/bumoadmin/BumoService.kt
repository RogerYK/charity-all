package bumoadmin

import bumoadmin.config.config
import com.alibaba.fastjson.JSONObject
import io.bumo.common.ToBaseUnit
import io.bumo.model.request.AccountGetNonceRequest
import io.bumo.model.request.TransactionBuildBlobRequest
import io.bumo.model.request.TransactionSignRequest
import io.bumo.model.request.TransactionSubmitRequest
import io.bumo.model.request.operation.AccountSetMetadataOperation
import io.bumo.model.request.operation.AssetIssueOperation
import io.bumo.model.request.operation.BaseOperation
import io.bumo.model.response.result.data.Signature


fun getNonce(): Long {
    val request = AccountGetNonceRequest().apply {
        address = config.address
    }

    val response = config.sdk.accountService.getNonce(request).orThrow()

    return response.result.nonce
}

fun buildOperation(): Array<BaseOperation> {
    val issuerAddress = config.address;
    val name = "Charity"
    val code = "CHY"
    val version = "1.0"
    val totalSupply = 100_000_000L
    val nowSupply = 100_000_000L
    val description = "Charity Token"
    val decimal = 0

    val assetIssueOperation = AssetIssueOperation().apply {
        sourceAddress = issuerAddress
        this.code = code
        amount = nowSupply
    }

    val atp10Json = JSONObject()
    atp10Json.putAll(mapOf(
            "name" to name,
            "code" to code,
            "description" to description,
            "decimals" to decimal,
            "totalSupply" to totalSupply,
            "version" to version
    ))

    val key = "asset_property_$code"
    val value = atp10Json.toJSONString()

    val accountSetMetadataOperation = AccountSetMetadataOperation().apply {
        sourceAddress = issuerAddress
        this.key = key
        this.value = value
    }

    return arrayOf(assetIssueOperation, accountSetMetadataOperation)
}

fun seralizeTransaction(nonce: Long, operations: Array<BaseOperation>): String {

    val senderAddress = config.address
    val gasPrice = 1000L
    val feeLimit = ToBaseUnit.BU2MO("50.03")

    val blobRequest = TransactionBuildBlobRequest().apply {
        sourceAddress = senderAddress
        this.nonce = nonce + 1
        this.feeLimit = feeLimit
        this.gasPrice = gasPrice
    }

    operations.forEach { blobRequest.addOperation(it) }

    val response = config.sdk.transactionService.buildBlob(blobRequest).orThrow()
    return response.result.transactionBlob
}

fun signTransaction(transactionBlob: String): Array<Signature> {
    val senderPrivateKey = config.privateKey

    val transactionSignRequest = TransactionSignRequest().apply {
        blob = transactionBlob
        addPrivateKey(senderPrivateKey)
    }

    val response = config.sdk.transactionService.sign(transactionSignRequest).orThrow()

    return response.result.signatures
}

fun submitTransaction(transactionBlob: String, signatures: Array<Signature>): String {
    val request = TransactionSubmitRequest().apply {
        this.transactionBlob = transactionBlob
        this.signatures = signatures
    }

    val response = config.sdk.transactionService.submit(request).orThrow()

    return response.result.hash
}