import React from 'react'
import { Progress, Button } from 'antd'

import './style.scss'

export const ProjectList = (props) => {

  return (
    <div className='project-list'>
      <div className='project-list-wrap'>{props.projects.map((p, i) => (
        <div key={i} className='project'>
          <img src={p.img} alt='img' />
          <div className='project-body'>
            <div className='project-content'>
              <div className='title'>{p.title}</div>
              <div className='introduction project-info-item'>
                <div className='label'>项目简介:</div>
                <div className='text'>{p.summary}</div>
              </div>
              <div className='project-info-item'>
                <div className='label'>筹款目标:</div>
                <div className='text'><span className='target-money'>{p.targetMoney}</span>元</div>
              </div>
              <div className='project-info-item'>
                <div className='label'>筹款时间:</div>
                <div className='text'>{`${p.startTime} 至 ${p.endTime} `}</div>
              </div>
              <div className='project-info-item'>
                <div className='label'>执 行 方:</div>
                <div className='text'>{p.author.nickName}</div>
              </div>
            </div>
            <div className='project-status'>
              <div className='project-info-item'>
                <div className='label'>项目状态:</div>
                <div className='text'>募捐中</div>
              </div>
              <div className='project-info-item'>
                <div className='label'>已筹:</div>
                <div className='text'><span className='raise-mark'>{p.raisedMoney}</span>元
                                    <span className='raise-mark'>{p.donorCount}</span>人次捐款
                                </div>
              </div>
              <Progress percent={0} />
              <div className='donation-option'>
                <Button size='small'>我要捐款</Button>
              </div>
            </div>
          </div>
        </div>
      ))}</div>
    </div>
  )
}
