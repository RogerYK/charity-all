package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.db.domain.CountAnalysis;
import com.github.rogeryk.charity.server.db.domain.vo.CountData;
import com.github.rogeryk.charity.server.db.repository.CountAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalysisService {
    @Autowired
    private CountAnalysisRepository countAnalysisRepository;

    public int sumUserCount() {
        return countAnalysisRepository.sumAllCount(CountAnalysis.CountType.User.ordinal());
    }

    public List<CountData> userCountByRange(Date startTime, Date endTime) {
        return countAnalysisRepository.findByRange(startTime, endTime, CountAnalysis.CountType.User.ordinal()).stream().map(CountData::from).collect(Collectors.toList());
    }

    public int sumProjectCount() {
        return countAnalysisRepository.sumAllCount(CountAnalysis.CountType.Project.ordinal());
    }

    public List<CountData> projectCountByRange(Date startTime, Date endTime) {
        return countAnalysisRepository.findByRange(startTime, endTime, CountAnalysis.CountType.Project.ordinal())
                .stream()
                .map(CountData::from)
                .collect(Collectors.toList());
    }

    public int sumTransactionCount() {
        return countAnalysisRepository.sumAllCount(CountAnalysis.CountType.Transaction.ordinal());
    }

    public List<CountData> transactionCountByRange(Date startTime, Date endTime) {
        return countAnalysisRepository.findByRange(startTime, endTime, CountAnalysis.CountType.Transaction.ordinal())
                .stream()
                .map(CountData::from)
                .collect(Collectors.toList());
    }
}
