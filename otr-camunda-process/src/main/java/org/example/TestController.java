package org.example;

import org.example.dto.Theme;
import org.example.dto.appeal.Appeal;
import org.example.dto.report.Report;
import org.example.dto.report.ReportStatus;
import org.example.dto.user.Employee;
import org.example.dto.user.RoleType;
import org.example.service.appeals.CamundaAppealService;
import org.example.service.report.CamundaReportService;
import org.example.service.user.CamundaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private CamundaAppealService appealService;
    @Autowired
    private CamundaReportService reportService;
    @Autowired
    private CamundaUserService userService;


    @GetMapping("appeal_create")
    public void appealCreate() {
        Appeal appeal = new Appeal();

        Theme theme = new Theme();
        theme.setName("theme");
        theme.setId(15L);

        appeal.setDescription("test");
        appeal.setId(11L);
//        appeal.setAmount(10);
//        appeal.setCreateDate(new Date());
//        appeal.setStatusAppeal(StatusAppeal.INPROCCESING);
//        appeal.setTheme(theme);

        appealService.create(appeal);
    }

    @GetMapping("report_create")
    public void reportCreate() {

        Appeal appeal = new Appeal();
        appeal.setId(11L);

        Report report = new Report();
        report.setAppeal(appeal);
        report.setReportStatus(ReportStatus.Rejected);
        report.setText("reject");


        reportService.create(report);

    }

    @GetMapping("user_appointed")
    public void userAppointed() {

    }

    @GetMapping("user_created")
    public void userCreated(){

        Employee employee = new Employee();
        employee.setId(12L);
        employee.setEmail("fajb420@gmail.com");
        employee.setName("we");
        employee.setLastName("qwe");
        employee.setPassword("12345");
        employee.setRole(RoleType.EMPLOYEE);


        userService.create(employee);
    }
}
