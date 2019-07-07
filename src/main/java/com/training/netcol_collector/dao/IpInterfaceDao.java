package com.training.netcol_collector.dao;

import com.training.netcol_collector.model.IpInterface;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IpInterfaceDao extends CrudRepository<IpInterface, Long> {
}
