import React from 'react'
import {Row, Col} from 'antd'

import './style.scss'

export default (props) => {
    return (
        <div className='home-aboutus section'>
            <div className='section-head'>关于我们</div>
            <div className='body'>
                <Row gutter={48}>
                    <Col span={8}>
                        <div className='aboutus-item'>
                        <div className='title'>平台简介</div>
                        <div className='content'>
                            <p>慈善平台就是给与人们一个方便给予和求救的平台</p>
                            <p>该平台使用使用互联网来夸大影响力，同时更加方便使用， 同时使用区块链技术来将人们的捐款信息以及现金流向全部写在区块链中，保证善款被正确使用。</p>
                        </div>
                        </div>
                    </Col>
                    <Col span={8}>
                        <div className='aboutus-item'>
                        <div className='title'>如何使用</div>
                        <div className='content'>
                            <p>慈善平台就是给与人们一个方便给予和求救的平台</p>
                            <p>该平台使用使用互联网来夸大影响力，同时更加方便使用， 同时使用区块链技术来将人们的捐款信息以及现金流向全部写在区块链中，保证善款被正确使用。</p>
                        </div>
                        </div>
                    </Col>
                    <Col span={8}>
                        <div className='aboutus-item'>
                        <div className='title'>如何保证善款不被滥用</div>
                        <div className='content'>
                            <p>慈善平台就是给与人们一个方便给予和求救的平台</p>
                            <p>该平台使用使用互联网来夸大影响力，同时更加方便使用， 同时使用区块链技术来将人们的捐款信息以及现金流向全部写在区块链中，保证善款被正确使用。</p>
                        </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
    )
}