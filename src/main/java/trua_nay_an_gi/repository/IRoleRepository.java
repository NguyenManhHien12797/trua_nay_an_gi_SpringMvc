package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.Roles;

public interface IRoleRepository extends IGeneralRepository<Roles> {

	@Override
	Roles findById(Long id);

	@Override
	void save(Roles t);

	@Override
	void update(Roles t);

	@Override
	void delete(Roles t);

	@Override
	List<Roles> findAll();

}
