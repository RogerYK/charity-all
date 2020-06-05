export interface Project {
  id: number;
  name: string;
  img: string;
}

export interface User {
  id: number;
  nickName: string;
  avatar: string;
}

export interface TableListItem {
  key: number;
  id: number;
  hash: string;
  type: string;
  money: number;
  createdTime: string;
  payer: User;
  payee: User;
  project: Project;
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

export interface TransactionListParams {
  id?: number;
  hash?: string;
  pageParam?: {page?: number; size?: number; direction?: string; field?: string;};
}
