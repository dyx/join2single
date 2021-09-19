package com.lhd.j2s.module.mock.controller;

import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.lhd.j2s.module.mock.dao.MockMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author lhd
 */
@Api(tags = "订单")
@RequestMapping("模拟数据")
@RestController
public class MockController {

    @Autowired
    private MockMapper mockMapper;

    @ApiOperation(value = "生成数据")
    @PostMapping
    public void generate() {
        mockMapper.clearUser();
        mockMapper.clearProduct();
        mockMapper.clearOrder();

        int userCount = 50 * 10000;
        int productCount = 50000;
        int orderCount = 500 * 10000;

        String rootPath = System.getProperty("user.dir");
        String dataPath = String.format("%s/user.csv", rootPath);
        CsvWriter csvWriter = new CsvWriter(dataPath);
        for (int i = 0; i < userCount; i++) {
            String[] cols = new String[5];
            cols[0] = String.valueOf(i + 1);
            cols[1] = RandomUtil.randomString(30).substring(0, RandomUtil.randomInt(5, 30));
            cols[2] = String.format("用户%s", (i + 1));
            cols[3] = String.format("1%s", RandomUtil.randomString("0123456789", 10));
            cols[4] = String.format("地址%s", (i + 1));
            csvWriter.write(cols);
        }
        mockMapper.insertUser(dataPath);

        dataPath = String.format("%s/product.csv", rootPath);
        csvWriter = new CsvWriter(dataPath);
        for (int i = 0; i < productCount; i++) {
            String[] cols = new String[3];
            cols[0] = String.format("P%s", StrUtil.padPre(String.valueOf(i + 1), 10, "0"));
            cols[1] = String.format("产品%s", (i + 1));
            cols[2] = RandomUtil.randomBigDecimal(BigDecimal.valueOf(100), BigDecimal.valueOf(10000)).toString();
            csvWriter.write(cols);
        }
        mockMapper.insertProduct(dataPath);

        dataPath = String.format("%s/order.csv", rootPath);
        csvWriter = new CsvWriter(dataPath);
        for (int i = 0; i < orderCount; i++) {
            String[] cols = new String[4];
            cols[0] = String.valueOf(RandomUtil.randomInt(1, userCount));
            cols[1] = String.valueOf(RandomUtil.randomInt(1, 4));
            cols[2] = String.valueOf(RandomUtil.randomInt(1, 3));
            cols[3] = String.format("P%s", StrUtil.padPre(String.valueOf(RandomUtil.randomInt(1, productCount)), 10, "0"));
            csvWriter.write(cols);
        }
        mockMapper.insertOrder(dataPath);
    }
}
