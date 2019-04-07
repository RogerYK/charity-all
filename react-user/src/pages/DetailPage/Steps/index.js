import React from 'react'

import './style.scss'

export default (props) => {
    return (
        <div {...props} className={'component-steps ' + props.className}>
            {props.children}
        </div>
    )
}

const Step = (props) => {
    return (
        <div className={'step ' + (props.active? 'active':'')}>
            <div className='title'>{props.title}</div>
            <div className='dot'></div>
        </div>
    )
}

export {Step}