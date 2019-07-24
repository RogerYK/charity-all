export interface Author {
  id: number;
  nickname: string;
}

export interface Category {
  id: number;
  name: string;
}

export interface TableListItem {
  key: number;
  id: number;
  name: string;
  img: string;
  gallery: string[];
  content: string;
  summary: string;
  raisedMoney: number;
  targetMoney: number;
  createdTime: string;
  startTime: string;
  endTime: string;
  bumoAddress: string;
  updateTime: string;
  donorCount: number;
  author: Author;
  category: Category;
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
