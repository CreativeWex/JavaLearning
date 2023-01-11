package Notes.Spring.boot.CRUD.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springcourse.DAO.PersonDAO;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String testingWithoutBatch() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String testingBatchUpdate() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }

    @GetMapping("/delete")
    public String deleteTestUnits() {
        personDAO.deleteTestUnits();
        return "redirect:/people";
    }
}
