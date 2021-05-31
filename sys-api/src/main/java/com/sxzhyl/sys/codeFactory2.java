package com.sxzhyl.sys;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 代码自动生成器
 * @author qbb
 *
 */
public class codeFactory2 {
    //作者
    private static final String author = "qbb";
    //数据库驱动
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    //数据库用户名
    private static final String userName = "root";
    //数据库用户密码
    private static final String password = "123456";
    //数据库连接
    private static final String url = "jdbc:mysql://172.23.156.19:3306/sx_lx?characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    //代码生成位置
    private static final String codePath = "/sys-api/src/main/java";
    //模块名
    private static final String moduleName = "com.sxzhyl.api";


    /**
     * codePath+moduleName 拼接成的路径是代码生成的具体路径，根据自己项目改动
     */
    public static void main(String[] args) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + codePath);
        globalConfig.setFileOverride(false);//是否覆盖之前的文件
        globalConfig.setSwagger2(true);
        globalConfig.setAuthor(author);
        globalConfig.setOpen(false);
        globalConfig.setDateType(DateType.ONLY_DATE);


        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        dsc.setUrl(url);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
//      strategy.setEntityBuilderModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setCapitalMode(true);
        strategy.setTablePrefix("P");
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude("user"); // 需要生成的表

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(moduleName);
        mpg.setPackageInfo(packageConfig);
        // 执行生成
        mpg.execute();
    }
}
