import { AnyAction, Reducer } from 'redux';
import { EffectsCommandMap } from 'dva';
import { removeProject, queryProject } from './service';

import { TableListData } from './data';

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
  };
  reducers: {
    save: Reducer<StateType>;
    remove: Reducer<StateType>;
  };
}

const Model: ModelType = {
  namespace: 'newsList',

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
          type: 'remove',
          payload,
        });
        if (callback) callback();
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
    remove(state, action) {
      const data = (state as StateType).data;
      return {
        data: {
          list: data.list.filter(p => p.id !== action.payload),
          pagination: data.pagination,
        },
      };
    },
  },
};

export default Model;
