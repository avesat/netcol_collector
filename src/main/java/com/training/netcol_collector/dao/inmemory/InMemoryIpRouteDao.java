package com.training.netcol_collector.dao.inmemory;

import com.training.netcol_collector.dao.IpRouteDao;
import com.training.netcol_collector.model.IpRoute;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InMemoryIpRouteDao implements IpRouteDao {

    @Override
    public IpRoute save(IpRoute route) {
        return route;
    }

    @Override
    public <S extends IpRoute> List<S> saveAll(Iterable<S> route) {
        return null;
    }

    @Override
    public Optional<IpRoute> findById(Long id) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }

    @Override
    public Iterable<IpRoute> findAll() {
        return null;
    }

    @Override
    public Iterable<IpRoute> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0L;
    }

    @Override
    public void deleteById(Long id) { }

    @Override
    public void delete(IpRoute route) { }

    @Override
    public void deleteAll(Iterable<? extends IpRoute> routes) { }

    @Override
    public void deleteAll() { }
}
