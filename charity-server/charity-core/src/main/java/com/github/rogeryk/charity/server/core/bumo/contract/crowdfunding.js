'use strict';

const assetCode = 'CHY';
const assetIssuer = 'buQYzuZW8XoeAGqsh7TKqAXYUWfswhPDocjA';
const donateCountKey = 'donated_count';
const donationPrefix = 'donation_';
const globalAttributeKey = 'global_attribute';
const raisedMoneyKey = 'raised_money';
const donationStatusKey = 'donation_status';
const Raise = 'raise';
const Success = 'success';
const Fail = 'fail';

function getMetadataOrDefault(key, defaultVal) {
    let val = Chain.load(key);
    if (val === false) {
        return defaultVal;
    } else {
        return val;
    }
}

function setObjectMetadata(key, data) {
    Chain.store(key, JSON.stringify(data));
}

function getObjectMetadata(key) {
    const data = Chain.load(key);
    return JSON.parse(data);
}

function recordDonation() {
    Utils.log('record donation');

    let count = Number(getMetadataOrDefault(donateCountKey, '0'));
    let raisedMoney = Number(getMetadataOrDefault(raisedMoneyKey, '0'));
    const msg = Chain.msg;
    const key = donationPrefix + String(count);
    raisedMoney = Utils.int64Add(raisedMoney, msg.asset.amount);
    setObjectMetadata(key, msg);
    Chain.store(donateCountKey, String(count+1));
    Chain.store(raisedMoneyKey, String(raisedMoney));
    Utils.log('record donation ' + count + ' success');

    const status = Chain.load(donationStatusKey);
    if (status === Success) {
        raiseSuccess();
    } else if (status === Fail) {
        Chain.payAsset(msg.sender, assetIssuer, assetCode, msg.asset.amount, "", "project raise has failed");
    }
}

function raiseSuccess() {
    const globalAttribute = getObjectMetadata(globalAttributeKey);
    Utils.log('raise success pay asset to help seeker: ' + globalAttribute.helpSeekerAddress);

    const assetKey = {
        'code': assetCode,
        'issuer': assetIssuer
    };

    const curAmount = Chain.getAccountAsset(Chain.thisAddress, assetKey);
    if (Utils.int64Compare(curAmount, 0) > 0) {
        Chain.payAsset(globalAttribute.helpSeekerAddress, assetIssuer, assetCode, curAmount, "", "raise success");
        Utils.log('pay asset to helper seeker success');
    } else {
        Utils.log('raise success but asset is 0');
    }
}

function raiseFail() {
    Utils.log('raised fail back asset to donor');

    const count = Number(getMetadataOrDefault(donateCountKey, '0'));
    let i = 0;
    for (i = 0; i < count; i += 1) {
        const key = donationPrefix + String(i);
        const record = getObjectMetadata(key);
        Chain.payAsset(record.sender, assetIssuer, assetCode, record.asset.amount, "", "raise fail");
    }

    Utils.log('back asset success');
}

function checkCondition() {
    const status = Chain.load(donationStatusKey);
    if (status === Raise) {
        const globalAttribute = getObjectMetadata(globalAttributeKey);
        Utils.log('checkout donation condition');
        const time = Chain.block.timestamp;
        const ft = Utils.int64Compare(time, globalAttribute.deadline);
        if (ft < 0) {
            Utils.log('missed deadline');
            return;
        }
        const curAmount = getMetadataOrDefault(raisedMoneyKey, '0');
        const f = Utils.int64Compare(curAmount, globalAttribute.donationTarget);
        if (f >= 0) {
            raiseSuccess();
        } else {
            raiseFail();
        }
    }
}

function init(input) {
    const params = JSON.parse(input);
    assert(Utils.stoI64Check(params.deadline) &&
        typeof params.helpSeekerAddress === 'string' &&
        Utils.stoI64Check(params.donationTarget), 'Failed to check args');

    const globalAttribute = {
        'deadline': params.deadline,
        'helpSeekerAddress': params.helpSeekerAddress,
        'donationTarget': params.donationTarget
    };
    setObjectMetadata(globalAttributeKey, globalAttribute);
    Chain.store(donationStatusKey, Raise);
}

function main(input) {
    Utils.log('begin run contract main');

    const code = Chain.msg.asset.key.code;
    const issuer = Chain.msg.asset.key.issuer;
    if (code === assetCode && issuer === assetIssuer) {
        recordDonation();
    }
    checkCondition();
}

function query(input) {
    return;
}

