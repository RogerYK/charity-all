import { AnyAction, Reducer } from 'redux';

import { EffectsCommandMap } from 'dva';
import moment from 'moment';
import { AnalysisData } from './data.d';
import {
  querySumUserCount,
  queryUserCountByRange,
  querySumProjectCount,
  queryProjectCount,
  querySumTransaction,
  queryTransactionCount,
} from './service';

export type Effect = (
  action: AnyAction,
  effects: EffectsCommandMap & { select: <T>(func: (state: AnalysisData) => T) => T },
) => void;

export interface ModelType {
  namespace: string;
  state: AnalysisData;
  effects: {
    fetch: Effect;
  };
  reducers: {
    save: Reducer<AnalysisData>;
  };
}

const initState: AnalysisData = {
  userCard: {
    total: 0,
    items: [],
  },
  projectCard: {
    total: 0,
    items: [],
  },
  transactionCard: {
    total: 0,
    items: [],
  },
  allChart: {
    userItems: [],
    projectItems: [],
    transactionItems: [],
  },
};

const timeFormat = 'YYYY-MM-DD HH:mm:ss';

const getUserCardData = async (startTime: string, endTime: string) => {
  const sumRes = await querySumUserCount();
  const { total } = sumRes.data;
  const countRes = await queryUserCountByRange({ startTime, endTime });
  const { items } = countRes.data;
  return { total, items };
};

const getProjectCardData = async (startTime: string, endTime: string) => {
  const sumRes = await querySumProjectCount();
  const { total } = sumRes.data;
  const countRes = await queryProjectCount({ startTime, endTime });
  const { items } = countRes.data;
  return { total, items };
};

const getTransactionCardData = async (startTime: string, endTime: string) => {
  const {
    data: { total },
  } = await querySumTransaction();
  const {
    data: { items },
  } = await queryTransactionCount({ startTime, endTime });
  return { total, items };
};

const getAllChartsData = async (startTime: string, endTime: string) => {
  const {
    data: { items: userItems },
  } = await queryUserCountByRange({ startTime, endTime });
  const {
    data: { items: projectItems },
  } = await queryProjectCount({ startTime, endTime });
  const {
    data: { items: transactionItems },
  } = await queryTransactionCount({ startTime, endTime });
  return { userItems, projectItems, transactionItems };
};

const Model: ModelType = {
  namespace: 'analysis',

  state: initState,

  effects: {
    *fetch(_, { call, put }) {
      const now = moment();
      const endTime = now.format(timeFormat);
      const startTime = now.subtract(14, 'days').format(timeFormat);
      const userCard = yield call(getUserCardData, startTime, endTime);
      const projectCard = yield call(getProjectCardData, startTime, endTime);
      const transactionCard = yield call(getTransactionCardData, startTime, endTime);
      const allChart = yield call(getAllChartsData, startTime, endTime);
      yield put({
        type: 'save',
        payload: { userCard, projectCard, transactionCard, allChart },
      });
    },
  },

  reducers: {
    save(state, { payload }) {
      return {
        ...state,
        ...payload,
      };
    },
  },
};

export default Model;
