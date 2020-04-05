package page.danya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import page.danya.controllers.RegisterController;
import page.danya.repository.GroupRepository;
import page.danya.repository.RoleRepository;
import page.danya.security.UserService;
import page.danya.security.jwt.JwtConfigurer;
import page.danya.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserService userService;

    private static final String LOGIN_ENDPOING = "/api/auth/login";
    private static final String REGISTER_ENDPOINT = "/api/auth/registration";
    private static final String ADMIN_ENDPOINT = "/api/admin/**";


    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOING, REGISTER_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole(RegisterController.ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }







    //    @Override            Old version
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/", "/registration", "/admin", "/admin/addStudentToGroup", "/admin/addStudentToGroup/link").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                .and()
//                    .logout()
//                    .permitAll();
//    }




//    @Override
//    protected void configure(final HttpSecurity http)
//            throws Exception {
//        http.csrf().disable().authorizeRequests()
//                    .antMatchers("/", "/registration", "/admin", "/admin/addStudentToGroup", "/admin/addStudentToGroup/link").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/homepage.html",true)
//                .failureUrl("/index.html?error=true");
//        //...
//    }
//




//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests().antMatchers("/").permitAll()
//                .and()
//                .csrf()//Disabled CSRF protection
//                .disable();
//
//    }


//    @Override
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userService);
////        auth.authenticationProvider(customAuthenticationProvider);
//        auth.authenticationProvider(authProvider());
//
//    }
//
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//






//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }



//    Google OAuth

//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
//    }
//
//
//    @Bean
//    public PrincipalExtractor principalExtractor(APP_UserRepository userDetailsRepo) {
//        return map -> {
//            String id = (String) map.get("sub");
//
//            APP_User user = userDetailsRepo.findById(id).orElseGet(() -> {
//                APP_User newUser = new APP_User();
//
//                newUser.setId(id);
//                newUser.setUsername((String) map.get("name"));
//                newUser.setEmail((String) map.get("email"));
//                newUser.setUserpic((String) map.get("picture"));
//
//                if (groupRepository.findById(1).isPresent() == false){
//                    Group group = new Group();
//                    group.setId(1);
//                    group.setName("Default group");
//
//                    group = groupRepository.save(group);
//                }
//
//
//                String ROLE_USER = "USER";
//                String ROLE_TEACHER = "TEACHER";
//                String ROLE_ADMIN = "ADMIN";
//
//                List<Role> roles = roleRepository.findAll();
//
//
//
//
//                for (int i = 0; i < roles.size(); i++) {
//                    if (roles.get(i).getName().equalsIgnoreCase(ROLE_USER)) {
//                        roleRepository.save(new Role(ROLE_USER));
//                    }
//                    if (roles.get(i).getName().equalsIgnoreCase(ROLE_TEACHER)) {
//                        roleRepository.save(new Role(ROLE_TEACHER));
//                    }
//                    if (roles.get(i).getName().equalsIgnoreCase(ROLE_ADMIN)) {
//                        roleRepository.save(new Role(ROLE_ADMIN));
//                    }
//                }
//
//                newUser.setFirstname("Не указан");
//                newUser.setLastname("Не указан");
//                newUser.setMiddlename("Не указан");
//                newUser.setTelnumber("Не указан");
//                newUser.setGroup(groupRepository.findById(1).get());  //set default value
//
//                List<Role> role = roleRepository.findByName(RegisterController.ROLE_USER);
//                newUser.setRole(role);
//
//
//
//                System.out.println("User= " + newUser.getUsername() + " had been registered"); //Log
//
//
//                return newUser;
//            });
//
//
//            return userDetailsRepo.save(user);
//        };
//    }












}

