export interface CountData {
  date: string;
  count: number;
}

export interface AnalysisData {
  userCard: {
    total: number;
    items: CountData[];
  };
  projectCard: {
    total: number;
    items: CountData[];
  };
  transactionCard: {
    total: number;
    items: CountData[];
  };

  allChart: {
    userItems: CountData[];
    projectItems: CountData[];
    transactionItems: CountData[];
  };
}
