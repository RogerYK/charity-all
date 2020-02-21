import React from 'react';
import { Card } from 'antd';

import styles from './index.less';

export interface ChartCardProps {
  loading?: boolean;
  title: React.ReactNode;
  action?: React.ReactNode;
  total?: React.ReactNode;
  footer?: React.ReactNode;
  contentHeight?: number;
  avatar?: React.ReactNode;
  style?: React.CSSProperties;
}

const ChartCard: React.FC<ChartCardProps> = props => {
  const {
    loading = false,
    contentHeight,
    title,
    avatar,
    action,
    total,
    footer,
    children,
    ...rest
  } = props;
  return (
    <Card loading={loading} bodyStyle={{ padding: '20px 24px 8px 24px' }} {...rest}>
      <div className={styles.chartCard}>
        <div className={styles.cardTop}>
          <div className={styles.metaWrap}>
            <div className={styles.meta}>
              <div className={styles.title}>{title}</div>
              <div className={styles.action}>{action}</div>
            </div>
            <div className={styles.total}>{total}</div>
          </div>
        </div>
        <div className={styles.content} style={{ height: contentHeight }}>
          {contentHeight ? <div className={styles.fixContent}>{children}</div> : children}
        </div>
        <div className={styles.footer}>{footer}</div>
      </div>
    </Card>
  );
};

export default ChartCard;
