import { AnyAction, Reducer } from 'redux';
import { EffectsCommandMap } from 'dva';
import { removeProject, queryProject, allowProject } from './service';

import { TableListData } from './data.d';

export interface StateType {
  data: TableListData;
}

export type Effect = (
  action: AnyAction,
  effects: EffectsCommandMap & { select: <T>(func: (state: StateType) => T) => T },
) => void;

export interface ModelType {
  namespace: string;
  state: StateType;
  effects: {
    fetch: Effect;
    remove: Effect;
    allow: Effect;
  };
  reducers: {
    save: Reducer<StateType>;
  };
}

const Model: ModelType = {
  namespace: 'projectList',

  state: {
    data: {
      list: [],
      pagination: {},
    },
  },

  effects: {
    *fetch({ payload = {} }, { call, put }) {
      const response = yield call(queryProject, payload);
      const page = payload.page || 1;
      const size = payload.size || 5;
      if (response.errCode === 0) {
        const data = response.data;
        yield put({
          type: 'save',
          payload: {
            list: data.content,
            pagination: {
              total: data.total,
              pageSize: size,
              current: page,
            },
          },
        });
      }
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(removeProject, payload);
      if (response.errCode === 0) {
        yield put({
          type: 'fetch',
          payload,
        });
        if (callback) callback();
      }
    },
    *allow({ payload }, { call, put }) {
      const response = yield call(allowProject, payload);
      if (response.errCode === 0) {
        yield put({
          type: 'fetch',
          payload: {},
        });
      }
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload,
      };
    },
  },
};

export default Model;
