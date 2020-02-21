import React, { useEffect } from 'react';
import { connect } from 'dva';
import { Dispatch } from 'redux';
import { Row, Col, Card, Tooltip } from 'antd';
import { InfoCircleOutlined } from '@ant-design/icons';
import ChartCard from './components/Charts/ChartCard';
import { AnalysisData } from './data.d';
import Field from './components/Charts/Field';
import MiniBar from './components/Charts/MiniBar';

import CompareLineChart from './components/Charts/CompareLine';

const colsProps = {
  xs: 24,
  sm: 12,
  md: 12,
  lg: 12,
  xl: 6,
  style: { marginBottom: 24 },
};

export interface AnalysisProps {
  analysis: AnalysisData;
  loading?: boolean;
  dispatch: Dispatch<any>;
}

const Analysis: React.FC<AnalysisProps> = props => {
  const {
    analysis: { userCard, projectCard, transactionCard, allChart },
    dispatch,
    loading,
  } = props;

  const convertFn = (v: { date: string; count: number }) => ({ x: v.date, y: v.count });
  const userData = userCard.items.map(convertFn);
  const projectData = projectCard.items.map(convertFn);
  const transactionData = transactionCard.items.map(convertFn);

  useEffect(() => {
    dispatch({
      type: 'analysis/fetch',
    });
  }, []);

  const compareData: { x: string; y1: number; y2: number; y3: number }[] = [];
  const mp = new Map<string, { x: string; y1?: number; y2?: number; y3?: number }>();
  allChart.userItems.forEach(v => {
    let o: { x: string; y1?: number; y2?: number; y3?: number } | null = null;
    if (mp.has(v.date)) {
      o = mp.get(v.date)!;
    } else {
      o = { x: v.date };
      mp.set(v.date, o);
    }
    o.y1 = v.count;
  });
  allChart.projectItems.forEach(v => {
    let o: { x: string; y1?: number; y2?: number; y3?: number } | null = null;
    if (mp.has(v.date)) {
      o = mp.get(v.date)!;
    } else {
      o = { x: v.date };
      mp.set(v.date, o);
    }
    o.y2 = v.count;
  });
  allChart.transactionItems.forEach(v => {
    let o: { x: string; y1?: number; y2?: number; y3?: number } | null = null;
    if (mp.has(v.date)) {
      o = mp.get(v.date)!;
    } else {
      o = { x: v.date };
      mp.set(v.date, o);
    }
    o.y3 = v.count;
  });
  mp.forEach(v => {
    compareData.push(v as { x: string; y1: number; y2: number; y3: number });
  });
  compareData.sort((a, b) => {
    if (a.x > b.x) return 1;
    if (a.x === b.x) return 0;
    return -1;
  });

  return (
    <div>
      <Row gutter={24}>
        <Col {...colsProps}>
          <ChartCard
            title="用户数"
            loading={loading}
            contentHeight={46}
            action={
              <Tooltip title="用户数量">
                <InfoCircleOutlined />
              </Tooltip>
            }
            total={userCard.total}
            footer={<Field label="今日人数" value="0" />}
          >
            <MiniBar data={userData} />
          </ChartCard>
        </Col>
        <Col {...colsProps}>
          <ChartCard
            title="项目数"
            loading={loading}
            contentHeight={46}
            action={
              <Tooltip title="用户数量">
                <InfoCircleOutlined />
              </Tooltip>
            }
            total={projectCard.total}
            footer={<Field label="新增项目" value="0" />}
          >
            <MiniBar data={projectData} />
          </ChartCard>
        </Col>
        <Col {...colsProps}>
          <ChartCard
            title="交易数"
            loading={loading}
            contentHeight={46}
            action={
              <Tooltip title="交易数量">
                <InfoCircleOutlined />
              </Tooltip>
            }
            total={projectCard.total}
            footer={<Field label="新增交易" value="0" />}
          >
            <MiniBar data={transactionData} />
          </ChartCard>
        </Col>
      </Row>
      <Card loading={loading} bordered={false} style={{ marginTop: 32 }}>
        <CompareLineChart
          height={400}
          data={compareData}
          titleMap={{
            y1: '用户数',
            y2: '项目数',
            y3: '订单数',
          }}
        />
      </Card>
    </div>
  );
};

export default connect(
  ({
    analysis,
    loading,
  }: {
    analysis: AnalysisData;
    loading: { models: { analysis?: boolean } };
  }) => ({ analysis, loading: loading.models.analysis }),
)(Analysis);
