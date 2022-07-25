package yxl.ServerContext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yxl.ServerContext.context.ServerContext;

/**
 * @Author: yxl
 * @Date: 2022/7/22 11:39
 */

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerContext serverContext;

    @PostMapping("ping")
    @ResponseBody
    public byte[] ping(@RequestBody byte[] data) {
        return serverContext.ping(data);
    }

}
