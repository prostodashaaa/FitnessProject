package com.example.fitnesproject.config;

import com.example.fitnesproject.domain.MembershipOption;
import com.example.fitnesproject.domain.FitnessMembership;
import com.example.fitnesproject.domain.UserRole;
import com.example.fitnesproject.domain.Users;
import com.example.fitnesproject.repo.FitnessMembershipRepo;
import com.example.fitnesproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

@Configuration
public class DBInitialFiller {
    private final FitnessMembershipRepo fitnessMembershipRepo;
    private final DataSource dataSource;
    private final UserService userService;
    @Autowired
    DBInitialFiller(FitnessMembershipRepo fitnessMembershipRepo,
                    DataSource dataSource,
                    UserService userService){
        this.fitnessMembershipRepo = fitnessMembershipRepo;
        this.dataSource = dataSource;
        this.userService = userService;
    }
    @Bean
    public CommandLineRunner fillDB(){
        return (args) -> {
            fillMembershipTable();
            createAndFillUserTable();
        };
    }

    public void fillMembershipTable(){
        fitnessMembershipRepo.save(FitnessMembership.builder()
                .name("Edward Cullen")
                .phone("8-800-200-23-16 ")
                .mail("228@228.su")
                .abonimentBoughtDate(LocalDate.now())
                .trainsLeft(3)
                .option(MembershipOption.VIP)
                .cost(22.2)
                .payedSum(0)
                .build());

        fitnessMembershipRepo.save(FitnessMembership.builder()
                .name("Monica Belucci")
                .phone("+72282282288")
                .mail("228@228.su")
                .abonimentBoughtDate(LocalDate.now())
                .trainsLeft(3)
                .option(MembershipOption.STANDARD)
                .cost(3.33)
                .payedSum(0)
                .build());

        fitnessMembershipRepo.save(FitnessMembership.builder()
                .name("Veronica Leal")
                .phone("+72282282288")
                .mail("228@228.su")
                .abonimentBoughtDate(LocalDate.now())
                .trainsLeft(3)
                .option(MembershipOption.STANDARD)
                .cost(4.44)
                .payedSum(0)
                .build());

        fitnessMembershipRepo.save(FitnessMembership.builder()
                .name("Bella Swan")
                .phone("+72282282288")
                .mail("228@228.su")
                .abonimentBoughtDate(LocalDate.now())
                .trainsLeft(3)
                .option(MembershipOption.STANDARD)
                .cost(5.55)
                .payedSum(0)
                .build());


        fitnessMembershipRepo
                .findAll()
                .forEach(System.out::println);
    }
    public void createAndFillUserTable(){
        try(Connection connection = dataSource.getConnection()){
            try(Statement statement = connection.createStatement()){
                statement.execute("""
                    create table users( 
                    username varchar_ignorecase(50) not null primary key,
                    password varchar_ignorecase(50) not null,
                    enabled boolean not null );
                    create table authorities (
                    username varchar_ignorecase(50) not null, 
                    authority varchar_ignorecase(50) not null,   
                    constraint fk_authorities_users foreign key(username) references users(username) );  
                    create unique index ix_auth_username on authorities (username,authority); 
                    """);
            } catch(Exception e) {
                System.out.println("couldnt create db to save users");
                System.exit(130);
            }
        } catch(Exception e) {
            System.out.println("couldnt connect db to save users");
            System.exit(130);
        }

        Users defaultUser = Users.builder()
                .userName("default_user")
                .password("user_pass")
                .userRole(UserRole.USER)
                .build();

        Users defaultAdmin = Users.builder()
                .userName("default_admin")
                .password("admin_pass")
                .userRole(UserRole.ADMIN)
                .build();

        userService.addUser(defaultUser);
        userService.addUser(defaultAdmin);

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select username, password from users;");
        ){
            while(res.next()){
                System.out.println(
                        res.getString(1)
                                + " "
                                + res.getString(2));
            }
        } catch(Exception e) {
            System.out.println("Couldnt connect db to read users");
            System.exit(130);
        }
    }
}
