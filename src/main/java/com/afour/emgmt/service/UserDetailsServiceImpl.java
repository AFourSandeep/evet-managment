/**
 * 
 */
package com.afour.emgmt.service;

/**
 * 
 */
public class UserDetailsServiceImpl{
////public class UserDetailsServiceImpl implements UserDetailsService{
//    @Autowired
//    private OrganizerRepository userRepository;
//
//    @Override
//    @Transactional()
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Organizer user = userRepository.findByUsername(username);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
////        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
////        }
//
//        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
//    }
}
