package com.auto.boot.bank.module.test.controller;

import com.auto.boot.bank.module.test.model.dto.TestAddDTO;
import com.auto.boot.bank.module.test.service.TestService;
import com.auto.boot.common.model.vo.ResultVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 测试控制器
 *
 * @author zhaohaifan
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    /**
     * 新增测试
     *
     * @param addDTO 请求参数
     * @return 返回结果
     */
    @PostMapping("/add.json")
    public ResultVO<Void> add(@RequestBody @Valid TestAddDTO addDTO) {
        testService.addTest(addDTO);
        return ResultVO.success();
    }
}
