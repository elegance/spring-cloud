package org.orh.spring.cloud.eureka.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/police")
public class PoliceController {

    @GetMapping("/{id}")
    public Police police(@PathVariable int id, HttpServletRequest request) {
        Police police = new Police();
        police.setId(id);
        police.setName("张警官");
        police.setNodeInfo(request.getRequestURL().toString()); // 设置当前服务节点的信息，让client调用端可以看到具体是哪个节点在提供服务
        return police;
    }
}
