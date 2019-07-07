package com.training.netcol_collector.dao;

import com.training.netcol_collector.model.IpRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IpRouteDao extends CrudRepository<IpRoute, Long> {
}
