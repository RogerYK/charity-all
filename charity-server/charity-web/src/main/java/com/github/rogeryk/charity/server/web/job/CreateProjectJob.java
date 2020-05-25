package com.github.rogeryk.charity.server.web.job;

import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import io.bumo.model.response.result.data.ContractAddressInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CreateProjectJob {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BumoService bumoService;

    @Scheduled(fixedRate = 5 * 60 * 1000)   //每五分钟执行一次
    public void checkCreatingProject() {
        log.info("begin check creating project");
        int limit = 50;
        long lastId = -1;
        while (true) {
            List<Project> projects = projectRepository.findCreatingProject(lastId, limit);
            for (Project project : projects) {
                try {
                    lastId = project.getId();
                    String hash = project.getTransactionHash();
                    int number = project.getTransactionNumber();
                    List<ContractAddressInfo> addressInfos = bumoService.getContractAddress(hash);
                    Optional<ContractAddressInfo> addressInfoOptional = addressInfos.stream().filter(info -> number == info.getOperationIndex()).findFirst();
                    if (addressInfoOptional.isPresent()) {
                        ContractAddressInfo addressInfo = addressInfoOptional.get();
                        project.setBumoAddress(addressInfo.getContractAddress());
                        project.setStatus(Project.ProjectStatus.APPLY);
                        projectRepository.saveAndFlush(project);
                    } else {
                        log.error("[project_id={}] project transaction hash can't get a contract address", project.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("check creating project fail {}", e.toString());
                }
            }
            if (projects.size() < limit) break;
        }
    }

//    @Scheduled(fixedRate = 5 * 60 * 1000)   //每五分钟执行一次
//    public void checkOverDueProject() {
//        log.info("begin check overdue project");
//        int limit = 50;
//        long start = -1;
//        while (true) {
//            List<Project> projects = projectRepository.findOverdueProjects(start, limit);
//            for (Project project : projects) {          //TODO 区块链中的智能合约处理。
//                try {
//                    if (project.getRaisedMoney().compareTo(project.getTargetMoney()) >= 0) {
//                        log.info("project raise success id={}", project.getId());
//                        project.setStatus(Project.ProjectStatus.SUCCESS);
//                    } else {
//                        log.info("project failed success id={}", project.getId());
//                        project.setStatus(Project.ProjectStatus.FAIL);
//                        bumoService.invokeContract(project.getBumoAddress()); //筹款失败需要手动触发合约将筹款退回
//                    }
//                    projectRepository.save(project);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    log.error("checkout overdue error :{}", e.toString());
//                }
//            }
//            if (projects.size() < limit) {
//                break; //说明已经处理结束
//            } else {
//                start = projects.get(limit-1).getId();
//            }
//        }
//    }
}
