package com.github.rogeryk.charity.server.web.admain.job;

import com.github.rogeryk.charity.server.core.util.TimeUtils;
import com.github.rogeryk.charity.server.db.domain.CountAnalysis;
import com.github.rogeryk.charity.server.db.domain.vo.CountData;
import com.github.rogeryk.charity.server.db.repository.CountAnalysisRepository;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DailyCountJob {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CountAnalysisRepository countAnalysisRepository;


    @Scheduled(fixedRate = 24*60*50*1000) //每24小时执行一次
    public void scanCount() {
        log.info("start scan count");
        LocalDate endTime = LocalDate.now();
        LocalDate startTime = endTime.plusDays(-14);
        scanUserCount(startTime, endTime);
        scanProjectCount(startTime, endTime);
        scanTransactionCount(startTime, endTime);
    }


    public void scanUserCount(LocalDate startTime, LocalDate endTime) {
        List<CountData> data = userRepository.scanCountData(TimeUtils.localDateToDate(startTime), TimeUtils.localDateToDate(endTime));
        data = fillData(startTime, endTime, data);
        updateCountAnalysis(data, CountAnalysis.CountType.User);
    }


    public void scanProjectCount(LocalDate startTime, LocalDate endTime) {
        List<Map<String, Object>> sourceData = projectRepository.scanCountData(TimeUtils.localDateToDate(startTime), TimeUtils.localDateToDate(endTime));
        List<CountData> data = sourceData.stream().map(v -> new CountData((String) v.get("data"), ((BigInteger) v.get("count")).intValue())).collect(Collectors.toList());
        data = fillData(startTime, endTime, data);
        log.info("project data {}", data);
        updateCountAnalysis(data, CountAnalysis.CountType.Project);
    }

    public void scanTransactionCount(LocalDate startTime, LocalDate endTime) {
        List<Map<String, Object>> sourceData = transactionRepository.scanCountData(TimeUtils.localDateToDate(startTime), TimeUtils.localDateToDate(endTime));
        List<CountData> data = sourceData.stream().map(v -> new CountData((String) v.get("data"), ((BigInteger) v.get("count")).intValue())).collect(Collectors.toList());
        data = fillData(startTime, endTime, data);
        updateCountAnalysis(data, CountAnalysis.CountType.Transaction);
    }

    public void updateCountAnalysis(List<CountData> data, CountAnalysis.CountType countType) {
        for (CountData cd : data) {
            Optional<CountAnalysis> row = countAnalysisRepository.findByDateEqualsAndCountTypeEquals(TimeUtils.localDateToDate(LocalDate.parse(cd.getDate(), dateTimeFormatter)), countType);
            CountAnalysis countAnalysis = row.orElseGet(() -> {
                CountAnalysis ca = new CountAnalysis();
                ca.setDate(TimeUtils.localDateToDate(LocalDate.parse(cd.getDate(), dateTimeFormatter)));
                ca.setCountType(countType);
                return ca;
            });
            countAnalysis.setCount(cd.getCount());
            countAnalysisRepository.save(countAnalysis);
        }
    }

    private List<CountData> fillData(LocalDate startTime, LocalDate endTime, List<CountData> data) {
        List<CountData> ans = new ArrayList<>();
        int i = 0;
        for (LocalDate t = startTime; !t.isAfter(endTime); t = t.plusDays(1)) {
            String dt = t.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            CountData cd = null;
            if (i < data.size()) {
                cd = data.get(i);
            }
            if (cd != null && dt.equals(cd.getDate())) {
                i++;
            } else {
                cd = new CountData(dt, 0);
            }
            ans.add(cd);
        }
        return ans;
    }
}
