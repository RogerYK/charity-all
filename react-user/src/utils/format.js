
export function convertMoneyStr(money) {
  return money.toString().replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
}