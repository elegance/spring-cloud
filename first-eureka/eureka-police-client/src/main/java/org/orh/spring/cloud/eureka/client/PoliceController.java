package org.orh.spring.cloud.eureka.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/police")
public class PoliceController {

    @GetMapping("/{id}")
    public Police police(@PathVariable int id) {
        Police police = new Police();
        police.setId(id);
        police.setName("张警官");
        return police;
    }
}
