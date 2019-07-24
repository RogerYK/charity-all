import { string } from 'prop-types';

export interface User {
  id: number;
  nickname: string;
}

export interface TableListItem {
  key: number;
  id: number;
  title: string;
  img?: string;
  content: string;
  introduce: string;
  createdTime: string;
  watchCount: number;
  favorCount: number;
  comentCount: number;
  author: User;
}

export interface TableListPagination {
  total: number;
  pageSize: number;
  current: number;
}

export interface TableListData {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
}

export interface TableListParams {
  page: number;
  size: number;
  direction: string;
  field: string;
}
