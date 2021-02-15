import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supyp.bghouse.dao.AccountMapper;
import com.supyp.bghouse.domain.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

public class JackSonTest {


    @Test
    public void other(){
//        ArrayList<String> list = new ArrayList<>();
//        System.out.println(list.isEmpty());
        System.out.println(123);
    }

    @Test
    public void obj2json(){
        ObjectMapper objectMapper = new ObjectMapper();
        // obj
        Account account = new Account();
        account.setRealname("吴杰辉");
        account.setPwd("123123");
        account.setIdcard("12312312313");
        account.setPhone("13680422246");
        // obj2json
        try {
            String jsonString = objectMapper.writeValueAsString(account);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void json2obj(){
        ObjectMapper objectMapper = new ObjectMapper();
        // json
        String jsonString = "{\"id\":null,\"username\":null,\"pwd\":\"123123\",\"realname\":\"吴杰辉\",\"idcard\":\"12312312313\",\"phone\":\"13680422246\"}";
        // json2obj
        try {
            Account account = objectMapper.readValue(jsonString, Account.class);
            System.out.println(account);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
