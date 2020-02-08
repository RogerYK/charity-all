'use strict';

const assetCode = 'CHY';
const assetIssuer = 'buQYzuZW8XoeAGqsh7TKqAXYUWfswhPDocjA';
const donateCountKey = 'donated_count';
const donationPrefix = 'donation_';
const globalAttributeKey = 'global_attribute';

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
    const msg = Chain.msg;
    const key = donationPrefix + String(count);
    setObjectMetadata(key, msg);
    Chain.store(donateCountKey, String(count+1));

    Utils.log('record donation ' + count + ' success');
}

function raiseSuccess() {
    const globalAttribute = getObjectMetadata(globalAttributeKey);
    Utils.log('raise success pay asset to help seeker: ' + globalAttribute.helpSeekerAddress);

    const assetKey = {
        'code': assetCode,
        'issuer': assetIssuer
    };

    const curAmount = Chain.getAccountAsset(globalAttribute.helpSeekerAddress, assetKey);
    Chain.payAsset(globalAttribute.helpSeekerAddress, assetIssuer, assetCode, curAmount, "", "raise success");

    Utils.log('pay asset to helper seeker success');
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
    const globalAttribute = getObjectMetadata(globalAttributeKey);
    Utils.log('checkout donation condition');
    const time = Chain.block.timestamp;
    const ft = Utils.int64Compare(time, globalAttribute.deadline);
    if (ft < 0) {
        Utils.log('missed deadline');
        return;
    }

    const assetKey = {
        'issuer': assetIssuer,
        'code': assetCode
    };
    const curAmount = Chain.getAccountAsset(Chain.thisAddress, assetKey);
    const f = Utils.int64Compare(curAmount, globalAttribute.donationTarget);
    if (f >= 0) {
        raiseSuccess();
    } else {
        raiseFail();
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
}

function main(input) {
    Utils.log('begin run contract main');

    const code = Chain.msg.asset.key.code;
    const issuer = Chain.msg.asset.key.issuer;
    if (code === assetCode && issuer === assetIssuer) {
        recordDonation();
        checkCondition();
    }
}

function query(input) {
    return;
}

