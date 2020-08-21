package pers.dafacloud.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pers.dafacloud.entity.UserParameters;
import pers.dafacloud.entity.UsersEnvironmentConfig;
import pers.dafacloud.server.UserParametersServer;
import pers.dafacloud.server.UsersEnvironmentConfigServer;

import java.util.List;

@Component
public class InitializeData implements CommandLineRunner {

    @Autowired
    UserParametersServer userParametersServer;

    @Autowired
    UsersEnvironmentConfigServer usersEnvironmentConfigServer;

    @Override
    public void run(String... args) {
        initUsersEnvironmentConfig();
    }

    public void initUserParameters() {
        List<UserParameters> list = userParametersServer.getUserParametersAllList();
        //System.out.println(list);
        for (UserParameters userParameters : list) {
            if (userParameters.getParameterType() == 1) {
                UserParametersMap.map.put("sys" + "_" + userParameters.getParameterName(), userParameters.getParameterValue());
            } else {
                UserParametersMap.map.put(userParameters.getUsername() + "_" + userParameters.getParameterName(), userParameters.getParameterValue());
            }
        }
        System.out.println(UserParametersMap.map);
    }

    public void initUsersEnvironmentConfig() {
        List<UsersEnvironmentConfig> list = usersEnvironmentConfigServer.getUserParametersActivitedList();
        //System.out.println(list);
        for (UsersEnvironmentConfig usersEnvironmentConfig : list) {
            UserParametersMap.evMap.put(usersEnvironmentConfig.getUsername() + "_" + usersEnvironmentConfig.getEvName(), usersEnvironmentConfig);
        }
        System.out.println(UserParametersMap.evMap);
    }
}
