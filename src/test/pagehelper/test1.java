package pagehelper;

import com.supyp.bghouse.dao.AccountMapper;
import com.supyp.bghouse.domain.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application.xml" })
public class test1 {
    @Resource
    private AccountMapper accountMapper;
    @Test
    public void test(){
        List<Account> accounts = accountMapper.selectAll();
        System.out.println(accounts);
    }
}
