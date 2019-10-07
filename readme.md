## 需求

公司雇员有三种类型。一种雇员是钟点工，系统会按照雇员记录中每小时报酬字段的值对他们进行支付。他们每天会提交工作时间卡，其中记录了日期以及工作小时数。如果他们每天工作超过8小时，超过部分会按照正常报酬的1.5倍进行支付。支付日期为每周五。月薪制的雇员以月薪进行支付。每个月的最后一个工作日对他们进行支付。在雇员记录中有月薪字段。销售人员会根据他们的销售情况支付一定数量的酬金（Commssion）。他们会提交销售凭条，其中记录了销售的日期和数量。在他们的雇员记录中有一个酬金报酬字段。每隔一周的周五对他们进行支付。

## 任务分解

* 确定是否支付日期
    * 确定是否为周五
    * 确定是否为月末工作日
        * 获取当月的假期信息
        * 确定当月的最后一个工作日
    * 确定是否为间隔一周周五
        * 获取上一次销售人员的支付日期
        * 确定是否间隔了一周
* 计算雇员薪资
    * 计算钟点工薪资
        * 获取钟点工雇员与工作时间卡
        * 根据雇员日薪计算薪资
    * 计算月薪雇员薪资
        * 获取月薪雇员与考勤记录
        * 对月薪雇员计算月薪
    * 计算销售人员薪资
        * 获取销售雇员与销售凭条
        * 根据酬金规则计算薪资
* 支付
    * 向满足条件的雇员账户发起转账
    * 生成支付凭条
    
### 时序图脚本

```
PaymentAppService.pay() {
    PaymentService.pay() {
        PayDayService.isPayday(today) {
            Calendar.isFriday(today);
            WorkdayService.isLastWorkday(today) {
                HolidayRepository.ofMonth(month);
                Calendar.isLastWorkday(holidays);
            }        
            WorkdayService.isIntervalFriday(today) {
                PaymentRepository.lastPayday(today);
                Calendar.isFriday(today);
            }
        }
        EmployeeRepository.allOf(employeeType);
        PayrollCalculator.calculate(employees) {
            HourlyEmployeePayrollCalculator.calculate() {
                HourlyEmployeeRepository.all();
                while (employee -> List<HourlyEmployee>) {
                    employee.payroll(PayPeriod);
                }
            }
            SalariedEmployeePayrollCalculator.calculate() {
                SalariedEmployeeRepository.all();
                while (employee -> List<SalariedEmployee>) {
                    employee.payroll();
                }
            }
            CommissionedEmployeePayrollCalculator.calculate() {
                CommissionedEmployeeRepository.all();
                while (employee -> List<CommissionedEmployee>) {
                    employee.payroll(payPeriod);
                }
            }
        }
        PayingPayrollService.execute(employees) {
            TransferClient.transfer(account);
            PaymentRepository.add(payment);
        }
    }
}
```

## 环境

开发环境：Java 8
数据库：MySQL 8.0 Community

### 数据库环境准备

项目默认的数据库用户名为sa，密码为123456，数据库URI为localhost。在安装了MySQL 8.0后，若数据库服务器信息与默认信息不同，请修改如下文件。

**flywaydb的数据库配置**

在`pom.xml`文件的`<plugins>`中配置了如下内容：

```xml
<plugin>
   <groupId>org.flywaydb</groupId>
   <artifactId>flyway-maven-plugin</artifactId>
   <version>6.0.4</version>
   <configuration>
      <driver>com.mysql.jdbc.Driver</driver>
      <url>jdbc:mysql://localhost:3306/payroll?serverTimezone=UTC</url>
      <user>sa</user>
      <password>123456</password>
   </configuration>
</plugin>
```

一旦准备好flywaydb的环境，就可以运行命令执行DB的清理：

```
mvn flyway:clean
```

或执行命令执行DB的迁移：

```
mvn flyway:migrate
```

**测试环境准备**

若要运行集成测试，应首先修改test/resources/META-INF文件夹下文件`persistence.xml`的相应内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
   http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">

    <persistence-unit name="PAYROLL_JPA" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <class>top.dddclub.payroll.employeecontext.domain.Employee</class>

        <properties>
            <property name="hibernate.connection.driver_class" value="com.mysql.cj.jdbc.Driver"/>
            <property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/payroll?serverTimezone=UTC"/>
            <property name="hibernate.connection.username" value="sa"/>
            <property name="hibernate.connection.password" value="123456"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
            <property name="hibernate.archive.autodetection" value="class"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
</persistence>
```

### 运行测试

默认情况下，如果运行`mvn test`则只会运行单元测试。如果确保数据库已经准备好，且通过flywaydb确保了数据库的表结构与测试数据已经准备好，则可以运行`mvn integration-test`。该命令会运行所有测试，包括单元测试和集成测试。

**注意：**项目中所有的单元测试以`Test`为测试类后缀，所有集成测试以`IT`为测试类后缀。