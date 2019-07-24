export interface User {
  id: number;
  nickname: string;
}

export interface Project {
  id: number;
  name: string;
}

export interface TableListItem {
  key: number;
  id: number;
  uniqueId: number;
  hash: string;
  type: string;
  money: number;
  createdTime: string;
  project?: Project;
  payer: User;
  payee: User;
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
