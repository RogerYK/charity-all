export interface User {
  id: number;
  nickname: string;
}

export interface Comment {
  id: number;
}

export interface TableListItem {
  key: number;
  id: number;
  content: string;
  commenter: User;
  replyComment?: Comment;
  createdTime: string;
  children?: TableListItem[];
}

export interface TableListPagination {
  total: number;
  pageSize: number;
  current: number;
}

export interface TableListDate {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
}

export interface TableListParams {
  sorter: string;
  status: string;
  pageSize: number;
  page: number;
}
