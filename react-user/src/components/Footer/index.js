import React from 'react'

import './style.scss'
import ghs from './ghs.png'
import itrust from './itrust.jpg'

export default (props) => {
    return (
        <div className='footer'>
            <div className="main-info">
                <div className="call-center">客服电话：400-000-4530</div>
                <div className="email-address">联系邮箱：rogeryk@outlook.com</div>
            </div>
            <div className="sub-info" >
                <div className="icp"><img src={ghs} alt="ghs" /> 泸ICP备16009820号-1</div>
                <div className="credit-level"><img src={itrust} alt="itrust" />信用级别：优良</div>
            </div>
            <div className='copyright'>校园慈善属于自主研发平台<br />Copyright&copyright;Zhongguo.All Rights Reserved</div>
        </div>
    )
}