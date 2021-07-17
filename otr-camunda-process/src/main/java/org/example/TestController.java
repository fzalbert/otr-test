package org.example;

import org.example.dto.TaskStatus;
import org.example.dto.Theme;
import org.example.dto.appeal.Appeal;
import org.example.dto.appeal.AppealStatusChangedDto;
import org.example.dto.appeal.StatusAppeal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
        appeal.setStatusAppeal(StatusAppeal.INPROCCESING.getValue());
        appeal.setTheme(theme);

//        appeal.setAmount(10);
//        appeal.setCreateDate(new Date());
//        appeal.setStatusAppeal(StatusAppeal.INPROCCESING);
//        appeal.setTheme(theme);

//        appealService.create(appeal);
    }

    @GetMapping("report_create")
    public void reportCreate() {

        Appeal appeal = new Appeal();
        appeal.setId(11L);

        Report report = new Report();
        report.setAppealId(11L);
        //report.setReportStatus(ReportStatus.Rejected);
        report.setText("reject");


        reportService.create(report);

    }

    @GetMapping("user_appointed")
    public void userAppointed() {

        Employee employee = createEmployee();

        appealService.appoint(employee.getLogin(), 11L);
    }

    @GetMapping("user_created")
    public void userCreated(){
        Employee employee = createEmployee();
        userService.create(employee);
    }

    @GetMapping("appeal_status")
    public void changeStatus(@RequestParam("status") int status){
        long appealId = 11L;
        Appeal appeal = new Appeal();

        Theme theme = new Theme();
        theme.setName("theme");
        theme.setId(15L);

        appeal.setDescription("test");
        appeal.setId(appealId);
        appeal.setStatusAppeal(StatusAppeal.INPROCCESING.getValue());
        appeal.setTheme(theme);

/*        AppealStatusChangedDto dto = new AppealStatusChangedDto(
                appeal,
                TaskStatus.values()[status]
        );*/
        //appealService.changeStatus(dto);

    }

    private Employee createEmployee(){
        Employee employee = new Employee();
        employee.setId(12L);
        employee.setLogin("fajb420");
        employee.setEmail("fajb420@gmail.com");
        employee.setName("we");
        employee.setLastName("qwe");
        employee.setPassword("12345");
        employee.setRole(RoleType.EMPLOYEE);
        return employee;
    }
}
