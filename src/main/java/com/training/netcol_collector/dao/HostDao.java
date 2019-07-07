package com.training.netcol_collector.dao;

import com.training.netcol_collector.model.Host;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HostDao extends CrudRepository<Host, Long> {
}
