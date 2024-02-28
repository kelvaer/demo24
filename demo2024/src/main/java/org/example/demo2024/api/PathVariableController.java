/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.demo2024.api;

import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.anno.CommonLog;
import org.example.demo2024.anno.RedisLimit;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.mapper.SysUserMapper;
import org.example.demo2024.util.Ip2regionUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@Slf4j
@Api(tags = "测试控制类")
public class PathVariableController {
    @Resource
    private SysUserMapper sysUserMapper;

    @GetMapping("/test")
    @CommonLog(value = "测试redisLimit")
    @RedisLimit(key = "testKey", limit = 5, timeout = 60)
    public ResultBody<Void> test() {
        return ResultBody.success();
    }

    @GetMapping("/t1")
    public ResultBody<String> te(){
        log.trace("trace---test!!!!!");
        log.info("info---test!!!!!");
        log.warn("warn---test!!!!!");
        log.debug("debug---test!!!!!");
        log.error("error---test!!!!!");
//        return "thirdTestApi测试!";
        return ResultBody.success("thirdTestApi测试!");
    }


    // http://127.0.0.1:8080/user/123/roles/222


    // http://127.0.0.1:8080/javabeat/somewords
    @RequestMapping(value = "/javabeat/{regexp1:[a-z-]+}", method = RequestMethod.GET)
    public String getRegExp(@PathVariable("regexp1") String regexp1) {
        return "URI Part : " + regexp1;
    }
}
