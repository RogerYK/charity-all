package bumoadmin

import bumoadmin.config.config

fun main(args: Array<String>) {
    createAccount()
}

fun publishAsset() {
    val operations = buildOperation();
    val nonce = getNonce()
    val blob = seralizeTransaction(nonce, operations)
    val signatures = signTransaction(blob)

    val hash = submitTransaction(blob, signatures)
    println("发行资产成功， hash:$hash")
}

fun createAccount() {
    var result = config.sdk.accountService.create().orThrow().result
    println("address: ${result.address}")
    println("private key: ${result.privateKey}")
}