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