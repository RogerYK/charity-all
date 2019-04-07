import React from 'react'
import {Row, Col} from 'antd'

import './style.css'
import set1 from './set1.png'
import set2 from './set2.png'
import set3 from './set3.png'
import set4 from './set4.png'

export default (props) => {
    return (
        <div className='home-advantages section'>
            <div className='section-head'>
                平台优势
            </div>
            <div className='body'>
                <Row gutter={30}>
                    <Col className='advantage' span='6'>
                        <img src={set1} alt='专业'/>
                        <h2>专业</h2>
                        <p>实力强大，专业专注，资金雄厚区块链技术保证信息安全</p>
                    </Col>
                    <Col className='advantage' span='6'>
                        <img src={set2} alt='安全'/>
                        <h2>安全</h2>
                        <p>所有善款只能在认证的善款机构使用</p>
                    </Col>
                    <Col className='advantage' span='6'>
                        <img src={set3} alt='公开'/>
                        <h2>公开</h2>
                        <p>每一笔善款都会公示</p>
                    </Col>
                    <Col className='advantage' span='6'>
                        <img src={set4} alt='透明'/>
                        <h2>透明</h2>
                        <p>资金流向，追本溯源<br />区块储存，不可篡改</p>
                    </Col>
                </Row>
            </div>
        </div>
    )
}