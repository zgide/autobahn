package net.geant.autobahn.aai;

import junit.framework.TestCase;

import net.geant.autobahn.constraints.ConstraintsNames;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccessRuleTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmptyRule() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setEmail("test@ROLEtest.com");
        uauth.setOrganization("ORG_testORG_Org");
        uauth.setProjectMembership("somORG_eProject");
        uauth.setProjectRole("testRolePM_");
        AccessRule arl = new AccessRule(uauth);
        arl.emptyRule();
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "");
    }
    
    @Test
    public void testEquals() {
        // Verify Access Rule equality and that starting/ending spaces are trimmed
        
        UserAuthParameters uauth1 = new UserAuthParameters();
        uauth1.setEmail("test@ROLEtest.com\t");
        uauth1.setOrganization("\nORG_testORG_Org");
        uauth1.setProjectMembership("somORG_eProject");
        uauth1.setProjectRole("testRolePM_");
        AccessRule arl1 = new AccessRule(uauth1);

        UserAuthParameters uauth2 = new UserAuthParameters();
        uauth2.setProjectRole("testRolePM_  ");
        uauth2.setProjectMembership("  somORG_eProject");
        uauth2.setOrganization(" ORG_testORG_Org");
        uauth2.setEmail("\btest@ROLEtest.com  ");
        AccessRule arl2 = new AccessRule(uauth2);

        TestCase.assertEquals(arl1, arl2);
    }
    
    @Test
    public void testCompleteRule() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setEmail("test@test.com");
        uauth.setOrganization("testOrg");
        uauth.setProjectMembership("someProject");
        uauth.setProjectRole("testRole");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_testRole EMAIL_test@test.com PM_someProject ORG_testOrg");
    }
    
    @Test
    public void testCompleteAttr() {
        String rule = "ROLE_testRole EMAIL_test@test.com PM_someProject ORG_testOrg";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getEmail(), "test@test.com");
        TestCase.assertEquals(arl.getAuthp().getOrganization(), "testOrg");
        TestCase.assertEquals(arl.getAuthp().getProjectMembership(), "someProject");
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "testRole");
    }
    
    @Test
    public void testRuleNoEmail() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setOrganization("SomeOrg");
        uauth.setProjectMembership("Myproj");
        uauth.setProjectRole("1");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_1 PM_Myproj ORG_SomeOrg");
    }
    
    @Test
    public void testAttrNoEmail() {
        String rule = "ROLE_1 PM_Myproj ORG_SomeOrg";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getOrganization(), "SomeOrg");
        TestCase.assertEquals(arl.getAuthp().getProjectMembership(), "Myproj");
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "1");
    }
    
    @Test
    public void testRuleNoEmailNoOrg() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setProjectMembership("$%^");
        uauth.setProjectRole(")(*");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_)(* PM_$%^");
    }
    
    @Test
    public void testAttrNoEmailNoOrg() {
        String rule = "ROLE_)(* PM_$%^";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getProjectMembership(), "$%^");
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), ")(*");
    }
    
    @Test
    public void testRuleRole() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setProjectRole("String_!@#$%^&*()~`?<>}{");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_String_!@#$%^&*()~`?<>}{");
    }
    
    @Test
    public void testAttrRole() {
        String rule = "ROLE_String_!@#$%^&*()~`?<>}{";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "String_!@#$%^&*()~`?<>}{");
    }
    
    @Test
    public void testRuleEmpty() {
        UserAuthParameters uauth = new UserAuthParameters();
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "");
    }
    
    @Test
    public void testAttrEmpty() {
        String rule = "";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "");
    }
    
    @Test
    public void testRuleSpaces() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setProjectRole("A role with spaces");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_A"+AccessRule.space_replacement+
                "role"+AccessRule.space_replacement+
                "with"+AccessRule.space_replacement+"spaces");
    }
    
    @Test
    public void testAttrSpaces() {
        String rule = "ROLE_A"+AccessRule.space_replacement+
            "role"+AccessRule.space_replacement+
            "with"+AccessRule.space_replacement+"spaces";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "A role with spaces");
    }
    
    @Test
    public void testRuleCommas() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setProjectRole("A,role,with,commas");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_A"+AccessRule.comma_replacement+
                "role"+AccessRule.comma_replacement+
                "with"+AccessRule.comma_replacement+"commas");
    }
    
    @Test
    public void testAttrCommas() {
        String rule = "ROLE_A"+AccessRule.comma_replacement+
            "role"+AccessRule.comma_replacement+
            "with"+AccessRule.comma_replacement+"commas";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "A,role,with,commas");
    }
    
    @Test
    public void testRuleMultipleSpaces() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setEmail("test@tes t.com");
        uauth.setOrganization("test  Org");
        uauth.setProjectMembership("so  meProject");
        uauth.setProjectRole("test \tRole");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_test"+AccessRule.space_replacement+
                "\tRole EMAIL_test@tes"+AccessRule.space_replacement+
                "t.com PM_so"+AccessRule.space_replacement+AccessRule.space_replacement+
                "meProject ORG_test"+AccessRule.space_replacement+AccessRule.space_replacement+
                "Org");
    }
    
    @Test
    public void testAttrMultipleSpaces() {
        String rule = "ROLE_test\t"+AccessRule.space_replacement+
            "Role EMAIL_test@tes"+AccessRule.space_replacement+
            "t.com PM_so"+AccessRule.space_replacement+AccessRule.space_replacement+
            "meProject ORG_test"+AccessRule.space_replacement+AccessRule.space_replacement+
            "Org";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getEmail(), "test@tes t.com");
        TestCase.assertEquals(arl.getAuthp().getOrganization(), "test  Org");
        TestCase.assertEquals(arl.getAuthp().getProjectMembership(), "so  meProject");
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "test\t Role");
    }
    
    @Test
    public void testRuleMultiplePrefixes() {
        UserAuthParameters uauth = new UserAuthParameters();
        uauth.setEmail("test@ROLEtest.com");
        uauth.setOrganization("ORG_testORG_Org");
        uauth.setProjectMembership("somORG_eProject");
        uauth.setProjectRole("testRolePM_");
        AccessRule arl = new AccessRule(uauth);
        String rule = arl.getRule();
        TestCase.assertEquals(rule, "ROLE_testRolePM_ EMAIL_test@ROLEtest.com PM_somORG_eProject ORG_ORG_testORG_Org");
    }
    
    @Test
    public void testAttrMultiplePrefixes() {
        String rule = "ROLE_testRolePM_ EMAIL_test@ROLEtest.com PM_somORG_eProject ORG_ORG_testORG_Org";
        AccessRule arl = new AccessRule(rule);
        TestCase.assertEquals(arl.getAuthp().getEmail(), "test@ROLEtest.com");
        TestCase.assertEquals(arl.getAuthp().getOrganization(), "ORG_testORG_Org");
        TestCase.assertEquals(arl.getAuthp().getProjectMembership(), "somORG_eProject");
        TestCase.assertEquals(arl.getAuthp().getProjectRole(), "testRolePM_");
    }
    
}
