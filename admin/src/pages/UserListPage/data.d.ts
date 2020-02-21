export interface TableListItem {
  key: number;
  id: number;
  phoneNumber: string;
  nickName: string;
  avatar: string;
  presentation: string;
  profession: string;
  address: string;
  sex: number;
  birthday: string;
  money: number;
  bumoAddress: string;
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

export interface UserListParams {
  id?: number;
  nickName?: string;
  phoneNumber?: string;
  pageParam?: {page?: number?; size?: number; direction?: string; field?: string;};
}