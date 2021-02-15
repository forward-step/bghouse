import com.github.pagehelper.PageInfo;
import com.supyp.bghouse.domain.dto.PaginationDto;
import com.supyp.bghouse.domain.entity.Account;
import com.supyp.bghouse.domain.entity.Authority;
import com.supyp.bghouse.domain.entity.Permission;
import com.supyp.bghouse.services.PermissionService;
import com.supyp.bghouse.services.impl.AuthorityServiceImpl;
import com.supyp.bghouse.services.impl.PermissionServiceImpl;
import com.supyp.bghouse.utils.ThreadLocalUtil;
import org.junit.Test;

import java.util.List;

public class permissiontest {
    @Test
    public void test(){
        PermissionService permissionService = new PermissionServiceImpl();
        AuthorityServiceImpl authorityService = new AuthorityServiceImpl();

        Account loginAccount = ThreadLocalUtil.get();
        // 1.找到所有的权限
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrent(1);
        paginationDto.setPageSize(9999);
        PageInfo<Permission> permissions = permissionService.findAll(paginationDto);
        // 2.找到我的授权
        List<Authority> authorities = authorityService.findAuthorityByRoleId(loginAccount.getId());
        // 3.根据我的授权，拼接我的
        System.out.println(permissions);
        System.out.println(authorities);
    }
}
