package com.test.ismg.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.test.ismg.modular.*.dao", "com.test.ismg.modular.*.*.dao"})
public class MybatisConfig {
}
