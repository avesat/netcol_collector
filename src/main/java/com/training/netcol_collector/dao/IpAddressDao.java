package com.training.netcol_collector.dao;

import com.training.netcol_collector.model.IpAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IpAddressDao extends CrudRepository<IpAddress, Long> {
}
