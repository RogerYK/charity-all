import React from 'react';
import { Chart, Axis, Geom, Tooltip, Legend } from 'bizcharts';
import DataSet from '@antv/data-set';

export interface CompareLineChartProps {
  data: {
    x: string;
    y1: number;
    y2: number;
    y3: number;
  }[];
  title?: string;
  height?: number;
  titleMap?: { y1: string; y2: string; y3: string };
  style?: React.CSSProperties;
}

const CompareLineChart: React.FC<CompareLineChartProps> = props => {
  const { data, title, height = 400, titleMap = { y1: 'y1', y2: 'y2', y3: 'y3' } } = props;

  const max = data.reduce((a, b) => Math.max(a, Math.max(b.y1, b.y2, b.y3)), 0);

  const dv = new DataSet().view();

  dv.source(data)
    .transform({
      type: 'map',
      callback: (row: { y1: number; y2: number; y3: number }) => {
        const newRow = { ...row };
        newRow[titleMap.y1] = row.y1;
        newRow[titleMap.y2] = row.y2;
        newRow[titleMap.y3] = row.y3;
        return newRow;
      },
    })
    .transform({
      type: 'fold',
      fields: [titleMap.y1, titleMap.y2, titleMap.y3],
      key: 'key',
      value: 'value',
    });
  const scale = {
    x: {
      type: 'cat',
    },
    value: {
      min: 0,
      max,
    },
  };

  return (
    <div>
      <div>{title}</div>
      <Chart data={dv} scale={scale} height={height} padding={[60, 20, 20, 40]} forceFit>
        <Axis name="x" />
        <Tooltip />
        <Legend name="key" position="top-right" />
        <Geom type="line" position="x*value" color="key" />
      </Chart>
    </div>
  );
};

export default CompareLineChart;
