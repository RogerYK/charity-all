import React, {Component} from 'react'
import { UserOutlined } from '@ant-design/icons';
import { Card, Avatar } from 'antd';



import './style.scss'
import p1 from './p1.png'
import p2 from './p2.png'
import p3 from './p3.png'

const staticProjects = [
    {
        name: '脚踏实地，砥砺前行',
        description: '用自己的一点爱心，去拯救一个未来。',
        projectType: '个人求助',
        targetMoney: 300000,
        raiseMoney: 113957,
        spportCount: 5662,
        img: p1,
    },
    {
        name: '脚踏实地，砥砺前行',
        description: '用自己的一点爱心，去拯救一个未来。',
        projectType: '公益活动',
        img: p2,
        targetMoney: 300000,
        raiseMoney: 113957,
        spportCount: 5662,
    },
    {
        name: '脚踏实地，砥砺前行',
        description: '用自己的一点爱心，去拯救一个未来。',
        projectType: '公益基金',
        img: p3,
        targetMoney: 300000,
        raiseMoney: 113957,
        spportCount: 5662,
    },
]

export default class ProjectShow extends Component {

    constructor() {
        super()
        this.state = {
            projects: staticProjects,
        }
    }

    getProjects() {
        return this.state.projects
            .map((p, i) => 
                    <Card 
                        key={i}
                        className="project"
                        bordered={false}
                        cover={<img alt='img' src={p.img} />}
                    >
                        <div className='project-author-info'>
                            <Avatar icon={<UserOutlined />} />
                            <div className='project-type'>{p.projectType}</div>
                        </div>
                        <div className='project-name'>{p.name}</div>
                        <div className='project-record'>
                            <div className='project-record-item'>
                                <div className='amount'>{p.targetMoney}<span className='suffix'>元</span></div>
                                <div className='label'>目标金额</div>
                            </div>
                            <div className='project-record-item'>
                                <div className='amount'>{p.raiseMoney}<span className='suffix'>元</span></div>
                                <div className='label'>获得金额</div>
                            </div>
                            <div className='project-record-item'>
                                <div className='amount'>{p.spportCount}<span className='suffix'>次</span></div>
                                <div className='label'>支持次数</div>
                            </div>
                        </div>
                    </Card>
            );
    }

    render() {
        return (
            <div className='project-show section'>
                <h2 className='section-head'>公益项目</h2>
                <div className='project-show-wrap body'>
                    {this.getProjects()}
                </div>
            </div>
        )
    }
}
