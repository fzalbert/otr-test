package org.example.service.appeals;

import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.StatusAppealParser;
import org.example.service.BaseCamundaService;
import org.springframework.stereotype.Service;


@Service
public class CamundaAppealService extends BaseCamundaService implements AppealService {

    @Override
    public void create(Appeal appeal) {
        System.out.println("appeal: " + appeal.getId());

        camunda.getRuntimeService()
                .startProcessInstanceByKey(appeal.getId().toString());

        camunda.getRuntimeService().createMessageCorrelation(appeal.getId().toString()) //
                .processInstanceBusinessKey(appeal.getId().toString())
                .setVariable("appeals_id", appeal.getId())
                .setVariable("appeal_client_name", appeal.getClientId())
                .setVariable("appeal_status", StatusAppealParser.toString(appeal.getStatusAppeal()))
                .setVariable("created_at", appeal.getCreateDate())
                .setVariable("appeal_theme", appeal.getTheme().getName())
                .setVariable("appeal_obj", appeal)
                .correlateWithResult();
    }

    @Override
    public void update(Appeal appeal) {

    }

}
