export interface TableListItem {
  key: number;
  id: number;
  userId: number;
  createdTime: string;
  updatedTime: string;
  identificationType: string;
  identificationState: string;
  identityCardName: string;
  identityCardNumber: string;
  identityCardPicture: string;
  email: string;
  phoneNumber: string;
  userPicture: string;
  sex: string;
  nation: string;
  province: string;
  city: string;
  region: string;
  detailAddress: string;
  profession: string;
  company: string;
  introduction: string;
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

export interface IdentificationListParams {
  states: string[];
  beginTime: string;
  endTime: string;
  pageParam: { page: number; size: number; direction: string; field: string };
}
