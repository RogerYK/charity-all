import { AnyAction, Reducer } from 'redux';
import { EffectsCommandMap } from 'dva';
import { queryRule, removeRule } from './service';

import { TableListDate } from './data.d';

export interface StateType {
  data: TableListDate;
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
  namespace: 'projectCommentList',

  state: {
    data: {
      list: [],
      pagination: {},
    },
  },

  effects: {
    *fetch({ payload = {} }, { call, put }) {
      const response = yield call(queryRule, payload);
      const page = payload.page || 1;
      const size = payload.size || 5;
      if (response && response.errCode === 0) {
        const { data } = response;
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
      const response = yield call(removeRule, payload);
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
          list: data.list.filter(c => c.id !== action.payload),
          pagination: data.pagination,
        },
      };
    },
  },
};

export default Model;
