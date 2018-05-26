package com.chengdu.jiq.service.rules;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jiyiqin on 2018/5/26.
 */
@Repository
public class UserInvestMessageRepository {

    public List<InvestMessage> select(String aid, Date beginDate, Date endDate, Integer limit) {
        InvestMessage invest1 = new InvestMessage(new BigDecimal(100000));
        InvestMessage invest2 = new InvestMessage(new BigDecimal(10000));
        InvestMessage invest3 = new InvestMessage(new BigDecimal(400090));
        InvestMessage invest4 = new InvestMessage(new BigDecimal(500));
        InvestMessage invest5 = new InvestMessage(new BigDecimal(4000));
        return Arrays.asList(invest1, invest2, invest3, invest4, invest5);
    }
}
