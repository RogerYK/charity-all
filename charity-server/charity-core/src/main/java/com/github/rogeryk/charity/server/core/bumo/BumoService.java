package com.github.rogeryk.charity.server.core.bumo;

import com.alibaba.fastjson.JSONObject;
import com.github.rogeryk.charity.server.core.bumo.util.AccountResult;
import com.github.rogeryk.charity.server.core.bumo.util.ResponseUtil;

import io.bumo.model.request.*;
import io.bumo.model.request.operation.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.TransactionGetInfoResult;
import io.bumo.model.response.result.data.ContractAddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.model.response.result.AccountCreateResult;
import io.bumo.model.response.result.TransactionSubmitResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class BumoService {

    @Autowired
    BumoConfig config;

    @Autowired
    SDK bumoSdk;

    public AccountResult createActiveAccount() {
        AccountCreateResponse createResponse = bumoSdk.getAccountService().create();
        if (createResponse.getErrorCode() != 0) {
            throw new BumoException(createResponse.getErrorCode(), createResponse.getErrorDesc());
        }
        AccountCreateResult createResult = createResponse.getResult();
        log.info("create bumo account address:"+createResult.getAddress() +
        ", privateKey:" + createResult.getPrivateKey());
        String hash = activeAccount(createResult.getAddress());
        log.info("active account address:" + createResult.getAddress());
        AccountResult result = new AccountResult();
        result.setAddress(createResult.getAddress());
        result.setPublicKey(createResult.getPublicKey());
        result.setPrivateKey(createResult.getPrivateKey());
        result.setHash(hash);
        return result;
    }

    //deadline 为16位的微妙时间戳
    public String createContract(String helpSeekerAddress, long target ,long deadline) {
        String createContractAddress = config.getFeeAccountAddress();
        Long initBalance = ToBaseUnit.BU2MO("0.01");
        String privateKey = config.getFeeAccountPrivateKey();

        JSONObject initInput = new JSONObject();
        initInput.put("helpSeekerAddress", helpSeekerAddress);
        initInput.put("deadline", String.valueOf(deadline));
        initInput.put("donationTarget", String.valueOf(target));
        String initInputStr = initInput.toJSONString();
        log.info("[create contract] init input:{}", initInputStr);

        ContractCreateOperation contractCreateOperation = new ContractCreateOperation();
        contractCreateOperation.setSourceAddress(createContractAddress);
        contractCreateOperation.setInitBalance(initBalance);
        contractCreateOperation.setInitInput(initInputStr);
        contractCreateOperation.setMetadata("create charity contract");
        contractCreateOperation.setPayload(config.getCrowdFundingContract());

        BlobAndSignatures blobAndSignatures = getBlobAndSignatures(contractCreateOperation,
                1000L,
                ToBaseUnit.BU2MO("10.08"),
                createContractAddress,
                getAccountNonce(createContractAddress)+1,
                privateKey
        );

        TransactionSubmitRequest submitRequest = new TransactionSubmitRequest();
        submitRequest.setTransactionBlob(blobAndSignatures.getBlob());
        submitRequest.setSignatures(blobAndSignatures.getSignatures());

        TransactionSubmitResponse submitResponse = ResponseUtil.check(
                bumoSdk.getTransactionService().submit(submitRequest)
        );

        log.info("[hash={}] create contract transaction success", submitResponse.getResult().getHash());

        return submitResponse.getResult().getHash();
    }

    public TransactionGetInfoResult getTransaction(String hash) {
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(hash);
        TransactionGetInfoResponse response = ResponseUtil.check(bumoSdk.getTransactionService().getInfo(request));
        return response.getResult();
    }


    public String recharge(String destAddress, Long amount) {
        return assetSend(config.getFeeAccountAddress(), destAddress, config.getFeeAccountPrivateKey(), amount);
    }

    public String assetSend(String sourceAddress, String destAddress, String privateKey, Long amount) {
        AssetSendOperation sendOperation = new AssetSendOperation();
        sendOperation.setSourceAddress(sourceAddress);
        sendOperation.setDestAddress(destAddress);
        sendOperation.setCode(config.getAssetCode());
        sendOperation.setIssuer(config.getAssetIssuer());
        sendOperation.setAmount(amount);

        BlobAndSignatures blobAndSignatures = getBlobAndSignatures(sendOperation,
                1000L,
                ToBaseUnit.BU2MO("0.01"),
                sourceAddress,
                getAccountNonce(sourceAddress)+1,
                privateKey
        );

        TransactionSubmitRequest submitRequest = new TransactionSubmitRequest();
        submitRequest.setTransactionBlob(blobAndSignatures.getBlob());
        submitRequest.setSignatures(blobAndSignatures.getSignatures());

        TransactionSubmitResponse submitResponse = ResponseUtil.check(
                bumoSdk.getTransactionService().submit(submitRequest)
        );

        return submitResponse.getResult().getHash();
    }

    public String activeAccount(String destAccountAddress) {
        String sourceAddress = config.getFeeAccountAddress();

        long nonce = getAccountNonce(sourceAddress);


        AccountActivateOperation activateOperation = new AccountActivateOperation();
        activateOperation.setSourceAddress(sourceAddress);
        activateOperation.setDestAddress(destAccountAddress);
        activateOperation.setInitBalance(ToBaseUnit.BU2MO("0.01"));
        activateOperation.setMetadata("激活账户");

        long gasPrice = 1000L;
        long feeLimit = ToBaseUnit.BU2MO("0.1");

        TransactionBuildBlobRequest blobRequest = new TransactionBuildBlobRequest();
        blobRequest.setSourceAddress(sourceAddress);
        blobRequest.setNonce(nonce+1);
        blobRequest.setFeeLimit(feeLimit);
        blobRequest.setGasPrice(gasPrice);
        blobRequest.addOperation(activateOperation);

        BlobAndSignatures blobAndSignatures = getBlogAndSignatures(blobRequest, new String[]{config.getFeeAccountPrivateKey()});


        TransactionSubmitRequest submitRequest = new TransactionSubmitRequest();
        submitRequest.setTransactionBlob(blobAndSignatures.getBlob());
        submitRequest.setSignatures(blobAndSignatures.getSignatures());

        TransactionSubmitResponse submitResponse = bumoSdk
                .getTransactionService().submit(submitRequest);

        if (submitResponse.getErrorCode() !=0) {
            throw new BumoException(submitResponse.getErrorCode(), submitResponse.getErrorDesc());
        }
        return submitResponse.getResult().getHash();
    }

    public void invokeContract(String address) {
        sendBu(address, ToBaseUnit.BU2MO("0.01"));
    }

    public void sendBu(String destAddress, long mo) {

        String sourceAddress = config.getFeeAccountAddress();

        BUSendOperation buSendOperation = new BUSendOperation();
        buSendOperation.setSourceAddress(sourceAddress);
        buSendOperation.setDestAddress(destAddress);
        buSendOperation.setAmount(mo);

        TransactionBuildBlobRequest blobRequest = new TransactionBuildBlobRequest();
        blobRequest.setSourceAddress(sourceAddress);
        blobRequest.setNonce(getAccountNonce(sourceAddress)+1);
        blobRequest.setFeeLimit(ToBaseUnit.BU2MO("0.01"));
        blobRequest.setGasPrice(1000L);

        BlobAndSignatures blobAndSignatures = getBlogAndSignatures(blobRequest, new String[]{config.getFeeAccountPrivateKey()});

        TransactionSubmitResult submitResult = submitTransaction(blobAndSignatures);
        log.info("send bu to "+destAddress+" "+mo+"mo, hash:"+submitResult.getHash());
    }



    public TransactionSubmitResult submitTransaction(BlobAndSignatures blobAndSignatures) {
        TransactionSubmitRequest request = new TransactionSubmitRequest();
        request.setTransactionBlob(blobAndSignatures.getBlob());
        request.setSignatures(blobAndSignatures.getSignatures());

        TransactionSubmitResponse response = bumoSdk.getTransactionService().submit(request);
        if (response.getErrorCode() != 0) {
            throw new BumoException(response.getErrorCode(), response.getErrorDesc());
        }
        return response.getResult();
    }

    public long getAccountNonce(String address) {
        AccountGetNonceRequest nonceRequest = new AccountGetNonceRequest();
        nonceRequest.setAddress(address);
        AccountGetNonceResponse nonceResponse = bumoSdk
                .getAccountService().getNonce(nonceRequest);

        if (nonceResponse.getErrorCode() != 0) {
            throw new BumoException(nonceResponse.getErrorCode(), nonceResponse.getErrorDesc());
        }
        return nonceResponse.getResult().getNonce();
    }

    public BlobAndSignatures getBlobAndSignatures(BaseOperation baseOperation,
                                                  Long gas,
                                                  Long feeLimit,
                                                  String sourceAddress,
                                                  Long nonce, String privateKey) {
        TransactionBuildBlobRequest blobRequest = new TransactionBuildBlobRequest();
        blobRequest.setSourceAddress(sourceAddress);
        blobRequest.addOperation(baseOperation);
        blobRequest.setNonce(nonce);
        blobRequest.setGasPrice(gas);
        blobRequest.setFeeLimit(feeLimit);

        TransactionBuildBlobResponse blobResponse = ResponseUtil.check(
                bumoSdk.getTransactionService().buildBlob(blobRequest)
        );
        String blob = blobResponse.getResult().getTransactionBlob();

        TransactionSignRequest signRequest = new TransactionSignRequest();
        signRequest.addPrivateKey(privateKey);
        signRequest.setBlob(blob);

        TransactionSignResponse signResponse = ResponseUtil.check(
                bumoSdk.getTransactionService().sign(signRequest)
        );

        return new BlobAndSignatures(blob, signResponse.getResult().getSignatures());
    }

    public BlobAndSignatures getBlogAndSignatures(TransactionBuildBlobRequest blobRequest,
                                                  String[] privateKeys) {
        TransactionBuildBlobResponse blobResponse = bumoSdk
                .getTransactionService()
                .buildBlob(blobRequest);
        if (blobResponse.getErrorCode() != 0) {
            throw new BumoException(blobResponse.getErrorCode(), blobResponse.getErrorDesc());
        }
        String blob = blobResponse.getResult().getTransactionBlob();


        TransactionSignRequest signRequest = new TransactionSignRequest();
        signRequest.setPrivateKeys(privateKeys);
        signRequest.setBlob(blob);

        TransactionSignResponse signResponse = bumoSdk
                .getTransactionService()
                .sign(signRequest);

        if (signResponse.getErrorCode() != 0) {
            throw new BumoException(signResponse.getErrorCode(), signResponse.getErrorDesc());
        }
        return new BlobAndSignatures(blob, signResponse.getResult().getSignatures());
    }

    public List<ContractAddressInfo> getContractAddress(String hash) {
        ContractGetAddressRequest request = new ContractGetAddressRequest();
        request.setHash(hash);
        ContractGetAddressResponse response = ResponseUtil.check(bumoSdk.getContractService().getAddress(request));
        return response.getResult().getContractAddressInfos();
    }
}
