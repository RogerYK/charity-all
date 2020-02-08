import React from 'react'
import {Link} from 'react-router-dom'
import ProjectCard from '../ProjectCard';

import styles from './style.module.scss'


const ProjectList = ({projects, bordered, cols, itemRender, itemWidth}) => {
  itemRender = itemRender || ((p, i) => (
    <Link key={i} to={`/detail/${p.id}`}>
      <ProjectCard bordered={bordered} project={p} />
    </Link>))
  const rows = []
  let row = []


  for (let i = 0; i < projects.length; i++) {
    const p = projects[i]
    row.push(itemRender(p, i))
    if ((i+1) % cols === 0) {
      rows.push(row)
      row = []
    } else {
      row.push(<div key={`${i}-spacer`} className={styles['spacer']} />)
    }
  }
  if (row.length !== 0) {
    for (let i = projects.length % cols; i < cols-1; i++) {
      row.push(<div key={i} style={{width: itemWidth}} />)
      row.push(<div key={`${i}-spacer`} className={styles['spacer']} />)
    }
    row.push(<div key={cols-1} style={{width: itemWidth}} />)
    rows.push(row)
  }

  return (
    <div className={styles['list']}>
      {rows.map((row, i) => <div key={i} className={styles['row']}>{row}</div>)}
    </div>
  )
}

export default ProjectList