package com.christian.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.christian.entity.UserRole;
import com.christian.repository.UserRepository;

/**
 * Implementa la clase que utiliza Spring Segurity para autenticar a los
 * usuarios
 * 
 * @author Christian Vilca
 * @version 1.0.0
 */
@Service("userService")
// Se implementa el servicio que Spring tiene para autenticar "UserDetailsService"
public class UserService implements UserDetailsService {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	/**
	 * Llama a nuestro repositorio "UserRepository" y se tendra que transformar
	 * nuestra entidad "User" a "UserDetails"
	 * 
	 * @param username
	 * @return UserDetails : Clase del Spring Security
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.christian.entity.User user = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAutorities(user.getUserRole());
		return buildUser(user, authorities);
	}

	/**
	 * Construye un User propio de Spring Security
	 * 
	 * @param user        Entidad User
	 * @param authorities
	 * @return org.springframework.security.core.userdetails.User : Spring Security
	 *         lo necesita
	 * @see GrantedAuthority : Objeto que Spring Security necesita para saber los
	 *      roles del usuario autenticado (en realidad es nuestra entidad rol)
	 */
	private User buildUser(com.christian.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
				// Podiamos poner estos campos en la tabla de bd
				// accountNonExpired -> No esta expirado la cuenta
				// credentialsNonExpired -> No estan expiradas las credenciales
				// accountNonLocked -> Si la cuenta no esta bloqueada
				true, true, true, authorities);
	}

	/**
	 * Convierte nuestros roles a una lista GrantedAuthority
	 * 
	 * @param userRole
	 * @return List<GrantedAuthority>
	 */
	private List<GrantedAuthority> buildAutorities(Set<UserRole> userRoles) {
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<GrantedAuthority>(auths);
	}
}
