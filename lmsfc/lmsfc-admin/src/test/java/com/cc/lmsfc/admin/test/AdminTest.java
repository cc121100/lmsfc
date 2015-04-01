package com.cc.lmsfc.admin.test;

import com.cc.lmsfc.admin.security.util.PasswordUtil;
import com.cc.lmsfc.common.dao.ResourceDAO;
import com.cc.lmsfc.common.dao.RoleDAO;
import com.cc.lmsfc.common.dao.UserDAO;
import com.cc.lmsfc.common.model.security.Resource;
import com.cc.lmsfc.common.model.security.Role;
import com.cc.lmsfc.common.model.security.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomchen on 15-3-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/test-spring-admin.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class AdminTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private ResourceDAO resourceDAO;


    @Test
    public void testSecurityInit(){

        try{

            PasswordUtil passwordUtil = new PasswordUtil();

            User user1 = new User();
            user1.setUserName("admin");
            user1.setPassword("123456");
            user1.setState(0);

            passwordUtil.encryptPassword(user1);


            Role adminRole = new Role();
            adminRole.setState(0);
            adminRole.setRoleName("admin");
            adminRole.setRoleDescription("admin");


            Resource r0 = new Resource();
            r0.setState(0);
            r0.setType("menu");
            r0.setResourceName("Filter管理");
            r0.setPermission("");
            r0.setParentResource(null);
            r0.setUrl("");

            List<Resource> resources = new ArrayList<>();
            resources.add(r0);

            resources.addAll(initSingleResource("Filter","filter","filter/list",r0));
            resources.addAll(initSingleResource("FilterRule","filterRule","filterRule/list",r0));
            resources.addAll(initSingleResource("FilterDetail","filterDetail","filterDetail/list",r0));

            Resource r1 = new Resource();
            r1.setState(0);
            r1.setType("menu");
            r1.setResourceName("文章管理");
            r1.setPermission("");
            r1.setParentResource(null);
            r1.setUrl("");

            resources.add(r1);
            resources.addAll(initSingleResource("文章","article","article/list",r1));
            resources.addAll(initSingleResource("文章元素","articleEle","articleEle/list",r1));

            Resource r2 = new Resource();
            r2.setState(0);
            r2.setType("menu");
            r2.setResourceName("任务管理");
            r2.setPermission("");
            r2.setParentResource(null);
            r2.setUrl("");

            resources.add(r2);
            resources.addAll(initSingleResource("任务","articleTask","articleTask/list",r2));
            resources.addAll(initSingleResource("批量任务","batchTask","batchTask/list",r2));



            for(Resource r : resources){
                resourceDAO.saveAndFlush(r);
            }
            adminRole.setResources(resources);
//            adminRole.setResources(resourceDAO.findAll());
            roleDAO.saveAndFlush(adminRole);

            List<Role> roles = new ArrayList<>();
            roles.add(adminRole);
            user1.setRoles(roles);
            userDAO.saveAndFlush(user1);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void deleteSecurityInit(){
        resourceDAO.deleteAll();
        roleDAO.deleteAll();
        userDAO.deleteAll();

    }

    @Test
    public void addFilterSecurity(){
        
    }

    @Test
    public void testDeleteInBatch(){

    }

    private List<Resource> initSingleResource(String resNamePrefix, String perNamePrefix, String subMenuUrl, Resource subMenuPar){
        List<Resource> list = new ArrayList<>();
        Resource r1 = new Resource();
        r1.setState(0);
        r1.setType("subMenu");
        r1.setResourceName(resNamePrefix + "管理");
        r1.setPermission(perNamePrefix+":view");
        r1.setParentResource(subMenuPar);
        r1.setUrl(subMenuUrl);

        Resource r2 = new Resource();
        r2.setState(0);
        r2.setType("button");
        r2.setResourceName("增加"+resNamePrefix);
        r2.setPermission(perNamePrefix+":add");
        r2.setParentResource(r1);
        r2.setUrl("");

        Resource r3 = new Resource();
        r3.setState(0);
        r3.setType("button");
        r3.setResourceName("修改"+resNamePrefix);
        r3.setPermission(perNamePrefix+":edit");
        r3.setParentResource(r1);
        r3.setUrl("");

        Resource r4 = new Resource();
        r4.setState(0);
        r4.setType("button");
        r4.setResourceName("删除"+resNamePrefix);
        r4.setPermission(perNamePrefix+":delete");
        r4.setParentResource(r1);
        r4.setUrl("");

        Resource r5 = new Resource();
        r5.setState(0);
        r5.setType("button");
        r5.setResourceName("查看"+resNamePrefix);
        r5.setPermission(perNamePrefix + ":view");
        r5.setParentResource(r1);
        r5.setUrl("");

        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
        list.add(r5);


        return list;

    }
}
